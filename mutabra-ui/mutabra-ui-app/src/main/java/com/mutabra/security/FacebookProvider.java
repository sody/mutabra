package com.mutabra.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.internal.Authorities;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.AbstractAuthenticationProvider;
import org.greatage.security.AuthenticationException;
import org.greatage.security.SecretEncoder;
import org.greatage.security.User;
import org.greatage.util.StringUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FacebookProvider extends AbstractAuthenticationProvider<User, FacebookToken> {
	private final OAuth2ServiceProvider<Facebook> facebookService;
	private final BaseEntityService<Account, AccountQuery> accountService;
	private final SecretEncoder secretEncoder;

	public FacebookProvider(final @InjectService("facebookService") OAuth2ServiceProvider<Facebook> facebookService,
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
		final Facebook facebook = facebookService.getApi(token.getCode());
		final FacebookProfile profile = facebook.userOperations().getUserProfile();

		if (StringUtils.isEmpty(profile.getId())) {
			throw new AuthenticationException("Invalid credentials");
		}
		Account account = accountService.query().withFacebook(profile.getId()).unique();
		if (account != null) {
			return authenticate(account);
		}

		if (StringUtils.isEmpty(profile.getEmail())) {
			throw new AuthenticationException("Invalid credentials");
		}
		account = accountService.query().withEmail(profile.getEmail()).unique();
		if (account != null) {
			account.setFacebookUser(profile.getId());
			return authenticate(account);
		}

		account = accountService.create();
		account.setFacebookUser(profile.getId());
		account.setEmail(profile.getEmail());
		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
		account.setRegistered(new Date());
		account.setName(profile.getName());
		account.setLocale(profile.getLocale());
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
