package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Role;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MainRealm extends AuthorizingRealm {
	private final BaseEntityService<Account> accountService;

	public MainRealm(final BaseEntityService<Account> accountService, final CredentialsMatcher credentialsMatcher) {
		super(credentialsMatcher);
		this.accountService = accountService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}

		final String username = (String) getAvailablePrincipal(principals);

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		try {
			return getAccount(username);
		} catch (RuntimeException ex) {
			throw new AuthorizationException(ex.getMessage(), ex);
		}
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		final String username = ((UsernamePasswordToken) token).getUsername();

		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		try {
			return getAccount(username);
		} catch (RuntimeException ex) {
			throw new AuthenticationException(ex.getMessage(), ex);
		}
	}

	private SimpleAccount getAccount(final String username) {
		final Account account = accountService.query()
				.filter(account$.email.eq(username))
				.unique();

		if (account == null) {
			return null;
		}

		final SimpleAccount simpleAccount = new SimpleAccount(account.getId(), account.getPassword(), getName());
		if (account.getSalt() != null) {
			final ByteSource salt = ByteSource.Util.bytes(Base64.decode(account.getSalt()));
			simpleAccount.setCredentialsSalt(salt);
		}

		final Role role = account.getRole();
		simpleAccount.addRole(role.getTranslationCode());
		simpleAccount.addStringPermissions(role.getPermissions());

		return simpleAccount;
	}
}
