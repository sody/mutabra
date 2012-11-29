package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;

import static com.mutabra.services.Mappers.account$;

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
        final Account account = getAccount(token);

        if (account == null) {
            throw new UnknownAccountException("Account not found");
        }
        if (account.getToken() == null) {
            throw new AccountException("Account has no pending changes");
        }
        final long currentTime = System.currentTimeMillis();
        if (account.getTokenExpired() == null || account.getTokenExpired() < currentTime) {
            throw new ExpiredCredentialsException(String.format(
                    "Submitted credentials for token [%s] has been expired.", token));
        }

        final String[] credentials = {account.getToken(), account.getPendingToken()};
        return new SimpleAuthenticationInfo(account.getId(), credentials, getName());
    }

    public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
        final String submittedToken = (String) token.getCredentials();
        final String[] accountTokens = (String[]) info.getCredentials();

        // submitted token can not be null
        if (submittedToken == null) {
            return false;
        }

        // retrieve account tokens
        final String mainToken = accountTokens[0];
        final String pendingToken = accountTokens[1];

        // main token matches
        if (submittedToken.equals(mainToken)) {
            final Account account = getAccount(token);
            // pending token is not null
            if (pendingToken != null) {
                // replace main token with pending token
                account.setToken(pendingToken);
                account.setPendingToken(null);
            } else {
                // apply all pending changes
                if (account.getPendingEmail() != null) {
                    final long count = accountService.query()
                            .filter(account$.email$.eq(account.getPendingEmail()))
                            .count();
                    if (count == 0) {
                        account.setEmail(account.getPendingEmail());
                    }
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
                account.setTokenExpired(null);
            }
            // save account changes and authenticate
            accountService.save(account);
            return true;
        }

        // pending token matches
        if (submittedToken.equals(pendingToken)) {
            final Account account = getAccount(token);
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

    private Account getAccount(final AuthenticationToken token) {
        final Long userId = ((Token) token).getUserId();
        return userId != null ? accountService.get(userId) : null;
    }

    public static class Token implements AuthenticationToken {
        private final Long userId;
        private final String token;

        public Token(final Long userId, final String token) {
            this.userId = userId;
            this.token = token;
        }

        public Long getUserId() {
            return userId;
        }

        public Object getPrincipal() {
            return userId;
        }

        public Object getCredentials() {
            return token;
        }
    }
}
