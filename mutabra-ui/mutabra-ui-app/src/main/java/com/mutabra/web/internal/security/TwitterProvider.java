//package com.mutabra.web.internal.security;
//
//import com.mutabra.domain.game.Account;
//import com.mutabra.domain.game.Role;
//import com.mutabra.security.OAuth;
//import com.mutabra.services.BaseEntityService;
//import com.mutabra.web.internal.Authorities;
//import com.mutabra.web.internal.security.TwitterToken;
//import org.apache.tapestry5.ioc.annotations.InjectService;
//import org.greatage.security.AbstractAuthenticationProvider;
//import org.greatage.security.AuthenticationException;
//import org.greatage.security.SecretEncoder;
//import org.greatage.security.User;
//import org.greatage.util.LocaleUtils;
//import org.greatage.util.StringUtils;
//
//import java.util.Date;
//import java.util.Map;
//
//import static com.mutabra.services.Mappers.account$;
//
///**
// * @author Ivan Khalopik
// * @since 1.0
// */
//public class TwitterProvider extends AbstractAuthenticationProvider<User, TwitterToken> {
//	private final BaseEntityService<Account> accountService;
//	private final SecretEncoder secretEncoder;
//
//
//	public TwitterProvider(final @InjectService("accountService") BaseEntityService<Account> accountService,
//						   final SecretEncoder secretEncoder) {
//		super(User.class, TwitterToken.class);
//		this.accountService = accountService;
//		this.secretEncoder = secretEncoder;
//	}
//
//	@Override
//	protected User doSignIn(final TwitterToken token) {
//		if (token.getSession() == null) {
//			throw new AuthenticationException("Invalid credentials");
//		}
//
//		final OAuth.Session session = token.getSession();
//
//		final Map<String, Object> profile = session.getProfile();
//
//		final String profileId = String.valueOf(profile.get("id"));
//		if (StringUtils.isEmpty(profileId)) {
//			throw new AuthenticationException("Invalid credentials");
//		}
//		Account account = accountService.query()
//				.filter(account$.twitterUser.eq(profileId))
//				.unique();
//		if (account != null) {
//			return authenticate(account);
//		}
//
//		account = accountService.create();
//		account.setTwitterUser(profileId);
//		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
//		account.setRegistered(new Date());
//		account.setName(String.valueOf(profile.get("name")));
//		account.setLocale(LocaleUtils.parseLocale(String.valueOf(profile.get("language"))));
//		//todo: account.setTimeZone(...);
//		//todo: account.setGender(...);
//
//		account.setRole(Role.USER);
//
//		return authenticate(account);
//	}
//
//	@Override
//	protected void doSignOut(final User authentication) {
//		//do nothing
//	}
//
//	private User authenticate(final Account account) {
//		account.setLastLogin(new Date());
//		accountService.saveOrUpdate(account);
//
//		return Authorities.createUser(account);
//	}
//}
