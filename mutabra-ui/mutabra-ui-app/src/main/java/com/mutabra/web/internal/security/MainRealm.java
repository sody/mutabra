package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Role;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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

		final Long userId = (Long) getAvailablePrincipal(principals);

		if (userId == null) {
			throw new AccountException("Null user identifiers are not allowed by this realm.");
		}

		try {
			final Account account = accountService.get(userId);

			if (account == null) {
				return null;
			}

			final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			final Role role = account.getRole();
			info.addRole(role.getTranslationCode());
			info.addStringPermissions(role.getPermissions());

			return info;
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
			final Account account = accountService.query()
					.filter(account$.email$.eq(username))
					.unique();

			if (account == null) {
				return null;
			}

			final SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(account.getId(), account.getPassword(), getName());
			if (account.getSalt() != null) {
				final ByteSource salt = ByteSource.Util.bytes(Base64.decode(account.getSalt()));
				info.setCredentialsSalt(salt);
			}

			return info;
		} catch (RuntimeException ex) {
			throw new AuthenticationException(ex.getMessage(), ex);
		}
	}
}
