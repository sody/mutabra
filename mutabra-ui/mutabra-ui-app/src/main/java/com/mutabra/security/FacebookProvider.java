package com.mutabra.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.internal.Authorities;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.json.JSONObject;
import org.greatage.security.AbstractAuthenticationProvider;
import org.greatage.security.AuthenticationException;
import org.greatage.security.SecretEncoder;
import org.greatage.security.User;
import org.greatage.util.LocaleUtils;
import org.greatage.util.StringUtils;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookProvider extends AbstractAuthenticationProvider<User, FacebookToken> {

	private final FacebookService facebookService;
	private final BaseEntityService<Account, AccountQuery> accountService;
	private final SecretEncoder secretEncoder;

	public FacebookProvider(final FacebookService facebookService,
							final @InjectService("accountService") BaseEntityService<Account, AccountQuery> accountService,
							final SecretEncoder secretEncoder) {
		super(User.class, FacebookToken.class);
		this.facebookService = facebookService;
		this.accountService = accountService;
		this.secretEncoder = secretEncoder;
	}

	@Override
	protected User doSignIn(final FacebookToken token) {
		if (StringUtils.isEmpty(token.getCode())) {
			throw new AuthenticationException("Invalid credentials");
		}
		//todo: change result value to map
		final JSONObject profile = facebookService.getProfile(token.getCode());
		final String username = (String) profile.get("id");
		if (StringUtils.isEmpty(username)) {
			throw new AuthenticationException("Invalid credentials");
		}
		Account account = accountService.query().withFacebook(username).unique();
		if (account != null) {
			return authenticate(account);
		}

		final String email = (String) profile.get("email");
		if (StringUtils.isEmpty(email)) {
			throw new AuthenticationException("Invalid credentials");
		}
		account = accountService.query().withEmail(email).unique();
		if (account != null) {
			account.setFacebookUser(username);
			return authenticate(account);
		}

		account = accountService.create();
		account.setEmail(email);
		account.setFacebookUser(username);
		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
		account.setRegistered(new Date());
		account.setName((String) profile.get("name"));
		account.setLocale(LocaleUtils.parseLocale((String) profile.get("locale")));
		//todo: account.setTimeZone(...);
		//todo: account.setGender(...);
		return authenticate(account);
	}

	@Override
	protected void doSignOut(final User authentication) {
		//do nothing
	}

	private User authenticate(final Account account) {
		account.setLastLogin(new Date());
		accountService.save(account);

		return Authorities.createUser(account);
	}
}
