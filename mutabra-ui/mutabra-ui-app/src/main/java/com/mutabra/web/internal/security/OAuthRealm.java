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
import org.greatage.domain.Repository;
import org.greatage.util.LocaleUtils;
import org.greatage.util.StringUtils;

import java.util.Date;
import java.util.Map;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class OAuthRealm<T extends OAuthToken> extends AuthenticatingRealm {
	private final RandomNumberGenerator rng = new SecureRandomNumberGenerator();

	private final BaseEntityService<Account> accountService;
	private final HashService hashService;

	public OAuthRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
					  final HashService hashService,
					  final Class<T> authenticationTokenClass) {
		this.accountService = accountService;
		this.hashService = hashService;

		setAuthenticationTokenClass(authenticationTokenClass);
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
			throws AuthenticationException {
		try {
			@SuppressWarnings("unchecked")
			final OAuth.Session session = ((T) token).getSession();

			final Map<String, Object> profile = session != null ? session.getProfile() : null;
			if (profile == null) {
				throw new AccountException("Null profiles are not allowed by this realm.");
			}

			final String profileId = String.valueOf(profile.get(OAuth.Session.ID));
			if (StringUtils.isEmpty(profileId)) {
				throw new AccountException("Invalid facebook identifier");
			}
			Account account = getAccountByProfileId(profileId);
			if (account != null) {
				return fillAccount(account);
			}

			final String email = String.valueOf(profile.get(OAuth.Session.EMAIL));
			if (!StringUtils.isEmpty(email)) {
				account = getAccountByEmail(email);
				if (account != null) {
					return attachAccount(account, profileId);
				}
			}

			return createAccount(profile);
		} catch (AuthenticationException ex) {
			// rethrow exception
			throw ex;
		} catch (RuntimeException ex) {
			// rethrow wrapped exception
			throw new AuthenticationException(ex.getMessage(), ex);
		}
	}

	protected abstract Account getAccountByProfileId(final String profileId);

	protected Account getAccountByEmail(final String email) {
		return findAccount(account$.email$.eq(email));
	}

	protected Account findAccount(final Repository.Criteria<Long, Account> criteria) {
		return accountService.query().filter(criteria).unique();
	}

	protected SimpleAccount fillAccount(final Account account) {
		final SimpleAccount simpleAccount = new SimpleAccount(account.getId(), null, getName());

		final Role role = account.getRole();
		simpleAccount.addRole(role.getTranslationCode());
		simpleAccount.addStringPermissions(role.getPermissions());

		return simpleAccount;
	}

	protected SimpleAccount attachAccount(final Account account, final String profileId) {
		accountService.save(account);
		return fillAccount(account);
	}

	protected SimpleAccount createAccount(final Map<String, Object> profile) {
		final Account account = accountService.create();
		account.setEmail(String.valueOf(profile.get(OAuth.Session.EMAIL)));
		account.setRegistered(new Date());
		account.setName(String.valueOf(profile.get(OAuth.Session.NAME)));
		account.setLocale(LocaleUtils.parseLocale(String.valueOf(profile.get(OAuth.Session.LOCALE))));
		//todo: account.setTimeZone(...);
		//todo: account.setGender(...);
		account.setRole(Role.USER);

		final Hash hash = generateHash();
		account.setPassword(hash.toBase64());
		if (hash.getSalt() != null) {
			account.setSalt(hash.getSalt().toBase64());
		}

		return attachAccount(account, String.valueOf(profile.get(OAuth.Session.ID)));
	}

	protected Hash generateHash() {
		return hashService.computeHash(new HashRequest.Builder().setSource(rng.nextBytes()).build());
	}
}
