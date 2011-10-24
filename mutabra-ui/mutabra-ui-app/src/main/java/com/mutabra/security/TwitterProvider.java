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
import org.greatage.util.LocaleUtils;
import org.greatage.util.StringUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.oauth1.OAuth1ServiceProvider;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterProvider extends AbstractAuthenticationProvider<User, TwitterToken> {
	private final OAuth1ServiceProvider<Twitter> twitterService;
	private final BaseEntityService<Account, AccountQuery> accountService;
	private final SecretEncoder secretEncoder;


	public TwitterProvider(final @InjectService("twitterService") OAuth1ServiceProvider<Twitter> twitterService,
			final @InjectService("accountService") BaseEntityService<Account, AccountQuery> accountService,
						   final SecretEncoder secretEncoder) {
		super(User.class, TwitterToken.class);
		this.twitterService = twitterService;
		this.accountService = accountService;
		this.secretEncoder = secretEncoder;
	}

	@Override
	protected User doSignIn(final TwitterToken token) {
		if (StringUtils.isEmpty(token.getToken()) || StringUtils.isEmpty(token.getSecret())) {
			throw new AuthenticationException("Invalid credentials");
		}

		final Twitter twitter = twitterService.getApi(token.getToken(), token.getSecret());
		final TwitterProfile profile = twitter.userOperations().getUserProfile();

		Account account = accountService.query().withTwitter(String.valueOf(profile.getId())).unique();
		if (account != null) {
			return authenticate(account);
		}

		account = accountService.create();
		account.setTwitterUser(String.valueOf(profile.getId()));
		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
		account.setRegistered(new Date());
		account.setName(profile.getName());
		account.setLocale(LocaleUtils.parseLocale(profile.getLanguage()));
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
