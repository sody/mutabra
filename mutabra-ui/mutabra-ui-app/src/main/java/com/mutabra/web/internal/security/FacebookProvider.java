//package com.mutabra.web.internal.security;
//
//import com.mutabra.domain.game.Account;
//import com.mutabra.domain.game.Role;
//import com.mutabra.services.BaseEntityService;
//import com.mutabra.web.internal.Authorities;
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
//public class FacebookProvider extends AbstractAuthenticationProvider<User, FacebookToken> {
//	private final BaseEntityService<Account> accountService;
//	private final SecretEncoder secretEncoder;
//
//	public FacebookProvider(final @InjectService("accountService") BaseEntityService<Account> accountService,
//							final SecretEncoder secretEncoder) {
//		super(User.class, FacebookToken.class);
//		this.accountService = accountService;
//		this.secretEncoder = secretEncoder;
//	}
//
//	@Override
//	protected User doSignIn(final FacebookToken token) {
//		if (token.getSession() == null) {
//			throw new AuthenticationException("Invalid credentials");
//		}
//		final Map<String, Object> profile = token.getSession().getProfile();
//
//		final String profileId = String.valueOf(profile.get("id"));
//		if (StringUtils.isEmpty(profileId)) {
//			throw new AuthenticationException("Invalid credentials");
//		}
//		Account account = accountService.query()
//				.filter(account$.facebookUser.eq(profileId))
//				.unique();
//		if (account != null) {
//			return authenticate(account);
//		}
//
//		final String email = String.valueOf(profile.get("email"));
//		if (StringUtils.isEmpty(email)) {
//			throw new AuthenticationException("Invalid credentials");
//		}
//		account = accountService.query()
//				.filter(account$.email.eq(email))
//				.unique();
//		if (account != null) {
//			account.setFacebookUser(profileId);
//			return authenticate(account);
//		}
//
//		account = accountService.create();
//		account.setFacebookUser(profileId);
//		account.setEmail(email);
//		account.setPassword(secretEncoder.encode(Authorities.generateSecret()));
//		account.setRegistered(new Date());
//		account.setName(String.valueOf(profile.get("name")));
//		account.setLocale(LocaleUtils.parseLocale(String.valueOf(profile.get("locale"))));
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
