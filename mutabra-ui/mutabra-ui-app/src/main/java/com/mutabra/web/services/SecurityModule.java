package com.mutabra.web.services;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.security.Facebook;
import com.mutabra.security.FacebookProvider;
import com.mutabra.security.Google;
import com.mutabra.security.OAuth;
import com.mutabra.security.OAuth2;
import com.mutabra.security.Twitter;
import com.mutabra.security.TwitterProvider;
import com.mutabra.security.VKontakte;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.Authority;
import com.mutabra.web.internal.AuthorityAnnotationExtractor;
import com.mutabra.web.internal.SecurityExceptionHandler;
import com.mutabra.web.internal.SecurityFilter;
import com.mutabra.web.internal.SecurityPersistenceFilter;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.greatage.security.Authentication;
import org.greatage.security.AuthenticationProvider;
import org.greatage.security.MessageDigestSecretEncoder;
import org.greatage.security.SecretEncoder;
import org.greatage.security.SecurityContext;
import org.greatage.security.SecurityContextImpl;
import org.greatage.security.User;
import org.greatage.security.UserCredentialsProvider;

import java.util.Date;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(SecurityContext.class, SecurityContextImpl.class);
	}

	@Scope(ScopeConstants.PERTHREAD)
	public AccountContext buildAccountContext(@InjectService("accountService") final BaseEntityService<Account> accountService,
											  final SecurityContext securityContext,
											  final HeroService heroService) {
		final Authentication user = securityContext.getCurrentUser();
		final Account account = user == null ? null : Authorities.isTwitterUser(user.getName()) ?
				accountService.query(account$.twitterUser.eq(Authorities.getTwitterUser(user.getName()))).unique() :
				accountService.query(account$.email.eq(user.getName())).unique();
		final Hero hero = account != null ? account.getHero() : null;
		final Battle battle = hero != null ? hero.getBattle() : null;

		if (hero != null) {
			hero.setLastActive(new Date());
			heroService.update(hero);
		}

		return new AccountContext() {
			public Account getAccount() {
				return account;
			}

			public Hero getHero() {
				return hero;
			}

			public Battle getBattle() {
				return battle;
			}
		};
	}

	public SecretEncoder buildSecretEncoder() {
		return new MessageDigestSecretEncoder("MD5", false);
	}

	@Contribute(SecurityContext.class)
	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration,
												@InjectService("accountService") final BaseEntityService<Account> accountService,
												final SecretEncoder secretEncoder) {
		configuration.add("token", new UserCredentialsProvider("token") {
			@Override
			protected User getAuthentication(final String key, final String secret) {
				final Account account = accountService.query(
						account$.email.eq(key),
						account$.token.eq(secret)
				).unique();

				if (account != null) {
					account.setToken(null);
					account.setLastLogin(new Date());
					if (account.getPendingPassword() != null) {
						account.setPassword(account.getPendingPassword());
						account.setPendingPassword(null);
					}
					if (account.getPendingEmail() != null && account.getPendingToken() == null) {
						account.setEmail(account.getPendingEmail());
						account.setPendingEmail(null);
					}
					accountService.saveOrUpdate(account);

					return Authorities.createUser(account);
				}

				return null;
			}
		});

		configuration.add("credentials", new UserCredentialsProvider(secretEncoder) {
			@Override
			protected User getAuthentication(final String key, final String secret) {
				final Account account = accountService.query(
						account$.email.eq(key),
						account$.password.eq(secret)
				).unique();

				if (account != null) {
					account.setLastLogin(new Date());
					accountService.saveOrUpdate(account);

					return Authorities.createUser(account);
				}

				return null;
			}
		});

		configuration.addInstance("facebook", FacebookProvider.class);
		configuration.addInstance("twitter", TwitterProvider.class);
	}

	@Contribute(MetaWorker.class)
	public void contributeMetaWorker(final MappedConfiguration<Class, MetaDataExtractor> configuration) {
		configuration.addInstance(Authority.class, AuthorityAnnotationExtractor.class);
	}

	@Contribute(MetaDataLocator.class)
	public void contributeMetaDataLocator(final MappedConfiguration<String, String> configuration) {
		configuration.add(Authorities.PAGE_AUTHORITY_META, "");
	}

	@Contribute(ComponentRequestHandler.class)
	public static void contributeComponentRequestHandler(final OrderedConfiguration<ComponentRequestFilter> configuration) {
		configuration.addInstance("SecurityFilter", SecurityFilter.class);
	}

	@Decorate(serviceInterface = RequestExceptionHandler.class)
	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final ResponseRenderer renderer) {
		return new SecurityExceptionHandler(handler, renderer, "security");
	}

	@Contribute(RequestHandler.class)
	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
		configuration.addInstance("SecurityPersistenceFilter", SecurityPersistenceFilter.class, "after:RepositorySessionFilter");
	}

	public OAuth2 buildFacebookService(@Symbol("facebook.app-id") final String clientId,
									   @Symbol("facebook.app-secret") final String clientSecret) {
		return new Facebook(clientId, clientSecret);
	}

	public OAuth buildTwitterService(@Symbol("twitter.consumer-key") final String consumerKey,
									 @Symbol("twitter.consumer-secret") final String consumerSecret) {
		return new Twitter(consumerKey, consumerSecret);
	}

	public OAuth buildGoogleService(@Symbol("google.consumer-key") final String consumerKey,
									@Symbol("google.consumer-secret") final String consumerSecret) {
		return new Google(consumerKey, consumerSecret);
	}

	public OAuth2 buildVkontakteService(@Symbol("vkontakte.consumer-key") final String consumerKey,
										@Symbol("vkontakte.consumer-secret") final String consumerSecret) {
		return new VKontakte(consumerKey, consumerSecret);
	}
}
