package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConfirmationRealm extends AuthenticatingRealm implements CredentialsMatcher {
	private final BaseEntityService<Account> accountService;

	public ConfirmationRealm(final BaseEntityService<Account> accountService) {
		this.accountService = accountService;
		setCredentialsMatcher(this);
		setAuthenticationTokenClass(Token.class);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		final Account account = ((Token) token).getAccount();

		if (account == null) {
			throw new UnknownAccountException("No account found");
		}

		return new SimpleAccount(
				account.getId(),
				new String[]{account.getToken(), account.getPendingToken()},
				getName());
	}

	public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
		final String submittedToken = getCredentials(token);
		final String[] accountTokens = getCredentials(info);

		// submitted token or account tokens is null
		if (submittedToken == null ||
				accountTokens == null ||
				accountTokens.length != 2 ||
				accountTokens[0] == null) {
			// illegal arguments
			return false;
		}

		// retrieve account tokens
		final String mainToken = accountTokens[0];
		final String pendingToken = accountTokens[1];

		// main token matches
		if (submittedToken.equals(mainToken)) {
			final Account account = ((Token) token).getAccount();
			// pending token is not null
			if (pendingToken != null) {
				// replace main token with pending token
				account.setToken(pendingToken);
				account.setPendingToken(null);
			} else {
				// apply all pending changes
				if (account.getPendingEmail() != null) {
					//todo: check if email is free
					account.setEmail(account.getPendingEmail());
					account.setPendingEmail(null);
				}
				if (account.getPendingPassword() != null) {
					account.setPassword(account.getPendingPassword());
					account.setSalt(account.getPendingSalt());
					account.setPendingPassword(null);
					account.setPendingSalt(null);
				}
				// reset token
				account.setToken(null);
			}
			// save account changes and authenticate
			accountService.save(account);
			return true;
		}

		// pending token matches
		if (submittedToken.equals(pendingToken)) {
			final Account account = ((Token) token).getAccount();
			// reset pending token
			account.setPendingToken(null);
			// save account changes
			accountService.save(account);
			// can not be authenticated as it comes not from original email
			return false;
		}

		// tokens doesn't match
		return false;
	}

	private String[] getCredentials(final AuthenticationInfo info) {
		return (String[]) info.getCredentials();
	}

	private String getCredentials(final AuthenticationToken token) {
		return (String) token.getCredentials();
	}

	public static class Token implements AuthenticationToken {
		private final Account account;
		private final String token;

		public Token(final Account account, final String token) {
			this.account = account;
			this.token = token;
		}

		public Account getAccount() {
			return account;
		}

		public Object getPrincipal() {
			return account;
		}

		public Object getCredentials() {
			return token;
		}
	}
}
