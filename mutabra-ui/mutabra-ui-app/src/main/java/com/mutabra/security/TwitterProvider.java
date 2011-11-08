package com.mutabra.security;

import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.internal.Authorities;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.AbstractAuthenticationProvider;
import org.greatage.security.AuthenticationException;
import org.greatage.security.SecretEncoder;
import org.greatage.security.User;
import org.greatage.util.LocaleUtils;
import org.greatage.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.mutabra.services.Mappers.account$;
import static com.mutabra.services.Mappers.role$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterProvider extends AbstractAuthenticationProvider<User, TwitterToken> {
	private final BaseEntityService<Account> accountService;
	private final BaseEntityService<Role> roleService;
	private final SecretEncoder secretEncoder;


	public TwitterProvider(final @InjectService("accountService") BaseEntityService<Account> accountService,
						   final @InjectService("roleService") BaseEntityService<Role> roleService,
						   final SecretEncoder secretEncoder) {
		super(User.class, TwitterToken.class);
		this.accountService = accountService;
		this.roleService = roleService;
		this.secretEncoder = secretEncoder;
	}

	@Override
	protected User doSignIn(final TwitterToken token) {
		if (token.getSession() == null) {
			throw new AuthenticationException("Invalid credentials");
		}

		final OAuth.Session session = token.getSession();

		final Map<String, Object> profile = session.getProfile();

		final String profileId = String.valueOf(profile.get("id"));
		if (StringUtils.isEmpty(profileId)) {
			throw new AuthenticationException("Invalid credentials");
		}
		Account account = accountService.query(account$.twitterUser.eq(profileId)).unique();
		if (account != null) {
			return authenticate(account);
		}

		account = accountService.create();
		account.setTwitterUser(profileId);
		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
		account.setRegistered(new Date());
		account.setName(String.valueOf(profile.get("name")));
		account.setLocale(LocaleUtils.parseLocale(String.valueOf(profile.get("language"))));
		//todo: account.setTimeZone(...);
		//todo: account.setGender(...);

		final Set<Role> roles = new HashSet<Role>(roleService.query(role$.code.eq("user")).list());
		account.setRoles(roles);

		return authenticate(account);
	}

	@Override
	protected void doSignOut(final User authentication) {
		//do nothing
	}

	private User authenticate(final Account account) {
		account.setLastLogin(new Date());
		accountService.saveOrUpdate(account);

		return Authorities.createUser(account);
	}
}
