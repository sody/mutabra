package com.mutabra.web.services;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.security.*;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.security.AccountQuery;
import com.mutabra.web.internal.SecurityExceptionHandler;
import com.mutabra.web.internal.SecurityPersistenceFilter;
import com.mutabra.web.pages.Security;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.greatage.security.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {
	private final RequestPageCache requestPageCache;
	private final ComponentClassResolver componentClassResolver;

	public SecurityModule(final RequestPageCache requestPageCache,
						  final ComponentClassResolver componentClassResolver) {
		this.requestPageCache = requestPageCache;
		this.componentClassResolver = componentClassResolver;
	}

	public static void bind(final ServiceBinder binder) {
		binder.bind(SecurityContext.class, SecurityContextImpl.class);
	}

	@Scope(ScopeConstants.PERTHREAD)
	public AccountContext buildAccountContext(@InjectService("accountService") final BaseEntityService<Account, AccountQuery> accountService,
											  final SecurityContext securityContext) {
		final Authentication user = securityContext.getCurrentUser();
		final Account account = user == null ? null : accountService.query().withEmail(user.getName()).unique();

		return new AccountContext() {
			public Account getAccount() {
				return account;
			}

			public Hero getHero() {
				return null;
			}
		};
	}

	public PasswordEncoder buildPasswordEncoder() {
		return new MessageDigestPasswordEncoder("MD5", false);
	}

	@Contribute(SecurityContext.class)
	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration,
												@InjectService("accountService") final BaseEntityService<Account, AccountQuery> accountService,
												final PasswordEncoder passwordEncoder) {
		configuration.add("mutabra", new PasswordAuthenticationProvider(passwordEncoder) {
			@Override
			protected PasswordAuthentication getAuthentication(final String name) {
				if (name != null) {
					final Account account = accountService.query().withEmail(name).unique();
					if (account != null) {
						return new PasswordAuthentication(account.getEmail(), account.getPassword(), account.getAuthorities());
					}
				}
				// not found
				return null;
			}

			@Override
			protected PasswordAuthentication doSignIn(final PasswordAuthenticationToken token) {
				final PasswordAuthentication authentication = super.doSignIn(token);
				authentication.getAuthorities().add(AuthorityConstants.STATUS_AUTHENTICATED);
				//todo: !!!remove this!!!
				authentication.getAuthorities().add(AuthorityConstants.ROLE_ADMIN);
				//todo: !!!remove this!!!
				return authentication;
			}
		});
	}

	public void contributeComponentClassTransformWorker(
			final OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
//		configuration.addInstance("PageSecurity", SecurityAnnotationWorker.class, "before:OnEvent");
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

	public TwitterService buildTwitterService(@Symbol("twitter.consumer-key") final String consumerKey,
											  @Symbol("twitter.consumer-secret") final String consumerSecret) {
		final Link link = createEventLink(Security.class, "twitterConnect");
		return new TwitterServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI());
	}

	public FacebookService buildFacebookService(@Symbol("facebook.app-id") final String appId,
												@Symbol("facebook.app-secret") final String appSecret) {
		final Link link = createEventLink(Security.class, "facebookConnect");
		return new FacebookServiceImpl(appId, appSecret, link.toAbsoluteURI());
	}

	public GoogleService buildGoogleService(@Symbol("google.consumer-key") final String consumerKey,
											@Symbol("google.consumer-secret") final String consumerSecret) {
		final Link link = createEventLink(Security.class, "googleConnect");
		return new GoogleServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI(), "https://mail.google.com/");
	}

	public VKontakteService buildVKontakteService(@Symbol("vkontakte.consumer-key") final String consumerKey,
												  @Symbol("vkontakte.consumer-secret") final String consumerSecret) {
		final Link link = createEventLink(Security.class, "vKontakteConnect");
		return new VKontakteServiceImpl(consumerKey, consumerSecret, link.toAbsoluteURI());
	}

	private Link createEventLink(final Class pageClass, final String eventType, final Object... context) {
		final String pageName = componentClassResolver.resolvePageClassNameToPageName(pageClass.getName());
		final Page page = requestPageCache.get(pageName);
		return page.getRootElement().createEventLink(eventType, context);
	}
}
