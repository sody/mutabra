package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Role;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.util.LocaleUtils;
import org.greatage.util.StringUtils;

import java.util.Date;
import java.util.Map;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookRealm extends AuthenticatingRealm {
	private final RandomNumberGenerator rng = new SecureRandomNumberGenerator();

	private final BaseEntityService<Account> accountService;
	private final HashService hashService;

	public FacebookRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
						 final HashService hashService) {
		this.accountService = accountService;
		this.hashService = hashService;

		setAuthenticationTokenClass(FacebookToken.class);
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
		try {
			final OAuth.Session session = ((FacebookToken) token).getSession();

			final Map<String, Object> profile = session != null ? session.getProfile() : null;
			if (profile == null) {
				throw new AccountException("Null profiles are not allowed by this realm.");
			}

			final String facebookId = String.valueOf(profile.get("id"));
			if (StringUtils.isEmpty(facebookId)) {
				throw new AccountException("Invalid facebook identifier");
			}
			Account account = accountService.query()
					.filter(account$.facebookUser.eq(facebookId))
					.unique();
			if (account != null) {
				return fillAccount(account);
			}

			final String email = String.valueOf(profile.get("email"));
			if (StringUtils.isEmpty(email)) {
				throw new AccountException("Invalid email address");
			}
			account = accountService.query()
					.filter(account$.email.eq(email))
					.unique();
			if (account != null) {
				account.setFacebookUser(facebookId);
				accountService.save(account);
				return fillAccount(account);
			}

			account = accountService.create();
			account.setFacebookUser(facebookId);
			account.setEmail(email);
			account.setRegistered(new Date());
			account.setName(String.valueOf(profile.get("name")));
			account.setLocale(LocaleUtils.parseLocale(String.valueOf(profile.get("locale"))));
			//todo: account.setTimeZone(...);
			//todo: account.setGender(...);
			account.setRole(Role.USER);

			final Hash hash = generateHash();
			account.setPassword(hash.toBase64());
			if (hash.getSalt() != null) {
				account.setSalt(hash.getSalt().toBase64());
			}
			accountService.save(account);

			return fillAccount(account);
		} catch (AuthenticationException ex) {
			// rethrow exception
			throw ex;
		} catch (RuntimeException ex) {
			// rethrow wrapped exception
			throw new AuthenticationException(ex.getMessage(), ex);
		}
	}

	private SimpleAccount fillAccount(final Account account) {
		final SimpleAccount simpleAccount = new SimpleAccount(account.getEmail(), null, getName());

		final Role role = account.getRole();
		simpleAccount.addRole(role.getTranslationCode());
		simpleAccount.addStringPermissions(role.getPermissions());

		return simpleAccount;
	}

	private Hash generateHash() {
		return hashService.computeHash(new HashRequest.Builder().setSource(rng.nextBytes()).build());
	}
}
