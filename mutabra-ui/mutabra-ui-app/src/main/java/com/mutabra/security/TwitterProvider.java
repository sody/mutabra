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

import java.util.Date;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterProvider extends AbstractAuthenticationProvider<User, TwitterToken> {
	private final OAuth twitterService;
	private final BaseEntityService<Account, AccountQuery> accountService;
	private final SecretEncoder secretEncoder;


	public TwitterProvider(final @InjectService("twitterService") OAuth twitterService,
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

		final OAuth.Session session = twitterService.connect(
				token.getToken(), token.getSecret(), token.getCallbackUrl(), token.getScope());

		final Map<String, Object> profile = session.getProfile();

		Account account = accountService.query().withTwitter(String.valueOf(profile.get("id"))).unique();
		if (account != null) {
			return authenticate(account);
		}

		account = accountService.create();
		account.setTwitterUser(String.valueOf(profile.get("id")));
		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
		account.setRegistered(new Date());
		account.setName((String) profile.get("name"));
		account.setLocale(LocaleUtils.parseLocale((String) profile.get("language")));
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
