package com.mutabra.web.services;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.security.FacebookProvider;
import com.mutabra.security.FacebookService;
import com.mutabra.security.FacebookServiceImpl;
import com.mutabra.security.GoogleService;
import com.mutabra.security.GoogleServiceImpl;
import com.mutabra.security.TwitterService;
import com.mutabra.security.TwitterServiceImpl;
import com.mutabra.security.VKontakteService;
import com.mutabra.security.VKontakteServiceImpl;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.SecurityAnnotationWorker;
import com.mutabra.web.internal.SecurityExceptionHandler;
import com.mutabra.web.internal.SecurityPersistenceFilter;
import com.mutabra.web.pages.Security;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.greatage.security.Authentication;
import org.greatage.security.AuthenticationProvider;
import org.greatage.security.MessageDigestSecretEncoder;
import org.greatage.security.SecretEncoder;
import org.greatage.security.SecurityContext;
import org.greatage.security.SecurityContextImpl;
import org.greatage.security.User;
import org.greatage.security.UserCredentialsProvider;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(SecurityContext.class, SecurityContextImpl.class);
	}

	@Scope(ScopeConstants.PERTHREAD)
	public AccountContext buildAccountContext(@InjectService("accountService") final BaseEntityService<Account, AccountQuery> accountService,
											  final SecurityContext securityContext) {
		final Authentication user = securityContext.getCurrentUser();
		final Account account = user == null ? null : Authorities.isTwitterUser(user.getName()) ?
				accountService.query().withTwitter(Authorities.getTwitterUser(user.getName())).unique() :
				accountService.query().withEmail(user.getName()).unique();

		return new AccountContext() {
			public Account getAccount() {
				return account;
			}

			public Hero getHero() {
				return null;
			}
		};
	}

	public SecretEncoder buildSecretEncoder() {
		return new MessageDigestSecretEncoder("MD5", false);
	}

	@Contribute(SecurityContext.class)
	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration,
												@InjectService("accountService") final BaseEntityService<Account, AccountQuery> accountService,
												final SecretEncoder secretEncoder) {
		configuration.add("token", new UserCredentialsProvider("token") {
			@Override
			protected User getAuthentication(final String key, final String secret) {
				final Account account = accountService.query()
						.withEmail(key)
						.withToken(secret)
						.unique();

				if (account != null) {
					account.setToken(null);
					account.setLastLogin(new Date());
					accountService.save(account);

					return Authorities.createUser(account);
				}

				return null;
			}
		});

		configuration.add("credentials", new UserCredentialsProvider(secretEncoder) {
			@Override
			protected User getAuthentication(final String key, final String secret) {
				final Account account = accountService.query()
						.withEmail(key)
						.withPassword(secret)
						.unique();

				if (account != null) {
					account.setLastLogin(new Date());
					accountService.save(account);

					return Authorities.createUser(account);
				}

				return null;
			}
		});

		configuration.addInstance("facebook", FacebookProvider.class);
	}

	public void contributeComponentClassTransformWorker(
			final OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
		configuration.addInstance("PageSecurity", SecurityAnnotationWorker.class, "after:OnEvent");
	}

	@Decorate(serviceInterface = RequestExceptionHandler.class)
	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final ResponseRenderer renderer,
																   final ComponentClassResolver resolver) {
		return new SecurityExceptionHandler(handler, renderer, resolver, Security.class);
	}

	@Contribute(RequestHandler.class)
	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
		configuration.addInstance("SecurityPersistenceFilter", SecurityPersistenceFilter.class);
	}

	public TwitterService buildTwitterService(final LinkManager linkManager,
											  @Symbol("twitter.consumer-key") final String consumerKey,
											  @Symbol("twitter.consumer-secret") final String consumerSecret) {
		final Link link = linkManager.createPageEventLink(Security.class, "twitterConnect");
		return new TwitterServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI());
	}

	public FacebookService buildFacebookService(final LinkManager linkManager,
												@Symbol("facebook.app-id") final String appId,
												@Symbol("facebook.app-secret") final String appSecret) {
		final Link link = linkManager.createPageEventLink(Security.class, "facebookConnect");
		return new FacebookServiceImpl(appId, appSecret, link.toAbsoluteURI(), "email");
	}

	public GoogleService buildGoogleService(final LinkManager linkManager,
											@Symbol("google.consumer-key") final String consumerKey,
											@Symbol("google.consumer-secret") final String consumerSecret) {
		final Link link = linkManager.createPageEventLink(Security.class, "googleConnect");
		return new GoogleServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI(), "https://mail.google.com/");
	}

	public VKontakteService buildVKontakteService(final LinkManager linkManager,
												  @Symbol("vkontakte.consumer-key") final String consumerKey,
												  @Symbol("vkontakte.consumer-secret") final String consumerSecret) {
		final Link link = linkManager.createPageEventLink(Security.class, "vKontakteConnect");
		return new VKontakteServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI());
	}
}
