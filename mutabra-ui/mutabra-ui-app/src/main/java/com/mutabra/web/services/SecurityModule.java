package com.mutabra.web.services;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.Role;
import com.mutabra.security.Facebook;
import com.mutabra.security.Google;
import com.mutabra.security.OAuth;
import com.mutabra.security.OAuth2;
import com.mutabra.security.Twitter;
import com.mutabra.security.VKontakte;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.internal.security.ConfirmationRealm;
import com.mutabra.web.internal.security.FacebookRealm;
import com.mutabra.web.internal.security.GoogleRealm;
import com.mutabra.web.internal.security.HashedPasswordMatcher;
import com.mutabra.web.internal.security.MainRealm;
import com.mutabra.web.internal.security.SecurityExceptionHandler;
import com.mutabra.web.internal.security.SecurityFilter;
import com.mutabra.web.internal.security.SecurityRequestFilter;
import com.mutabra.web.internal.security.TwitterRealm;
import com.mutabra.web.internal.security.VKRealm;
import com.mutabra.web.pages.Security;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.meta.FixedExtractor;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {
	private static final String SECURITY_TOKEN_EXPIRATION_TIME = "security.token-expiration-time";

	private static final String SECURITY_HASH_ALGORITHM = "security.hash-algorithm";
	private static final String SECURITY_HASH_ITERATIONS = "security.hash-iterations";
	private static final String SECURITY_PRIVATE_SALT = "security.private-salt";

	private static final String SECURITY_FACEBOOK_KEY = "facebook.app-id";
	private static final String SECURITY_FACEBOOK_SECRET = "facebook.app-secret";

	private static final String SECURITY_TWITTER_KEY = "twitter.consumer-key";
	private static final String SECURITY_TWITTER_SECRET = "twitter.consumer-secret";

	private static final String SECURITY_GOOGLE_KEY = "google.consumer-key";
	private static final String SECURITY_GOOGLE_SECRET = "google.consumer-secret";

	private static final String SECURITY_VK_KEY = "vk.consumer-key";
	private static final String SECURITY_VK_SECRET = "vk.consumer-secret";

	@ApplicationDefaults
	@Contribute(SymbolProvider.class)
	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SECURITY_HASH_ALGORITHM, Sha512Hash.ALGORITHM_NAME);
		configuration.add(SECURITY_HASH_ITERATIONS, "512");
		configuration.add(SECURITY_PRIVATE_SALT, "8carxXOr0uNa8aqhCYZZZA==");

		configuration.add(SECURITY_TOKEN_EXPIRATION_TIME, "60000");
	}

	public WebEnvironment buildWebEnvironment(final ApplicationGlobals applicationGlobals,
											  final WebSecurityManager securityManager) {
		final DefaultWebEnvironment environment = new DefaultWebEnvironment();
		environment.setServletContext(applicationGlobals.getServletContext());
		environment.setWebSecurityManager(securityManager);
		return environment;
	}

	public WebSecurityManager buildWebSecurityManager(final List<Realm> realms) {
		return new DefaultWebSecurityManager(realms);
	}

	@Contribute(WebSecurityManager.class)
	public void contributeWebSecurityManager(final OrderedConfiguration<Realm> configuration,
											 @InjectService("accountService") final BaseEntityService<Account> accountService,
											 final HashedPasswordMatcher generator) {
		configuration.add("main", new MainRealm(accountService, generator));
		configuration.add("facebook", new FacebookRealm(accountService, generator));
		configuration.add("twitter", new TwitterRealm(accountService, generator));
		configuration.add("google", new GoogleRealm(accountService, generator));
		configuration.add("vk", new VKRealm(accountService, generator));
		configuration.add("confirmation", new ConfirmationRealm(accountService));

		if (accountService.query().count() <= 0) {
			final Account account = accountService.create();
			account.setEmail("admin@mutabra.com");
			account.setName("admin");
			account.setRole(Role.ADMIN);
			account.setRegistered(new Date());

			accountService.save(account);
		}
	}

	public HashedPasswordMatcher buildHashedPasswordMatcher(@Symbol(SECURITY_HASH_ALGORITHM) final String hashAlgorithmName,
															@Symbol(SECURITY_HASH_ITERATIONS) final int hashIterations,
															@Symbol(SECURITY_PRIVATE_SALT) final String privateSalt,
															@Symbol(SECURITY_TOKEN_EXPIRATION_TIME) final long tokenExpirationTime) {

		final DefaultHashService hashService = new DefaultHashService();
		hashService.setHashAlgorithmName(hashAlgorithmName);
		hashService.setHashIterations(hashIterations);
		hashService.setPrivateSalt(ByteSource.Util.bytes(Base64.decode(privateSalt)));
		hashService.setGeneratePublicSalt(true);

		return new HashedPasswordMatcher(hashService, tokenExpirationTime);
	}

	public OAuth2 buildFacebookService(@Symbol(SECURITY_FACEBOOK_KEY) final String clientId,
									   @Symbol(SECURITY_FACEBOOK_SECRET) final String clientSecret,
									   final PageRenderLinkSource linkSource) {
		final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".facebook:connected";
		return new Facebook(clientId, clientSecret, redirectUri);
	}

	public OAuth buildTwitterService(@Symbol(SECURITY_TWITTER_KEY) final String consumerKey,
									 @Symbol(SECURITY_TWITTER_SECRET) final String consumerSecret,
									 final PageRenderLinkSource linkSource) {
		final String callbackUrl = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".twitter:connected";
		return new Twitter(consumerKey, consumerSecret, callbackUrl);
	}

	public OAuth2 buildGoogleService(@Symbol(SECURITY_GOOGLE_KEY) final String consumerKey,
									 @Symbol(SECURITY_GOOGLE_SECRET) final String consumerSecret,
									 final PageRenderLinkSource linkSource) {
		final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".google:connected";
		return new Google(consumerKey, consumerSecret, redirectUri);
	}

	public OAuth2 buildVkService(@Symbol(SECURITY_VK_KEY) final String consumerKey,
								 @Symbol(SECURITY_VK_SECRET) final String consumerSecret,
								 final PageRenderLinkSource linkSource) {
		final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".vk:connected";
		return new VKontakte(consumerKey, consumerSecret, redirectUri);
	}

	@Contribute(HttpServletRequestHandler.class)
	public void contributeHttpServletRequestHandler(final OrderedConfiguration<HttpServletRequestFilter> configuration) {
		configuration.addInstance("shiro", SecurityRequestFilter.class);
	}

	@Contribute(ComponentRequestHandler.class)
	public static void contributeComponentRequestHandler(final OrderedConfiguration<ComponentRequestFilter> configuration) {
		configuration.addInstance("SecurityFilter", SecurityFilter.class);
	}

	@Contribute(MetaWorker.class)
	public void contributeMetaWorker(final MappedConfiguration<Class, MetaDataExtractor> configuration) {
		configuration.add(RequiresAuthentication.class, new FixedExtractor(SecurityFilter.SHIRO_REQUIRES_AUTHENTICATION_META));
		configuration.add(RequiresUser.class, new FixedExtractor(SecurityFilter.SHIRO_REQUIRES_USER_META));
		configuration.add(RequiresGuest.class, new FixedExtractor(SecurityFilter.SHIRO_REQUIRES_GUEST_META));
		configuration.add(RequiresRoles.class, new MetaDataExtractor<RequiresRoles>() {
			public void extractMetaData(final MutableComponentModel model, final RequiresRoles annotation) {
				final Iterator<String> iterator = Arrays.asList(annotation.value()).iterator();
				final StringBuilder builder = new StringBuilder();
				if (iterator.hasNext()) {
					builder.append(iterator.next());
					while (iterator.hasNext()) {
						builder.append(",");
						builder.append(iterator.next());
					}
				}

				model.setMeta(SecurityFilter.SHIRO_REQUIRES_ROLES_META, builder.toString());
				model.setMeta(SecurityFilter.SHIRO_REQUIRES_ROLES_LOGICAL_META,
						String.valueOf(annotation.logical() == Logical.AND));
			}
		});
		configuration.add(RequiresPermissions.class, new MetaDataExtractor<RequiresPermissions>() {
			public void extractMetaData(final MutableComponentModel model, final RequiresPermissions annotation) {
				final Iterator<String> iterator = Arrays.asList(annotation.value()).iterator();
				final StringBuilder builder = new StringBuilder();
				if (iterator.hasNext()) {
					builder.append(iterator.next());
					while (iterator.hasNext()) {
						builder.append(",");
						builder.append(iterator.next());
					}
				}

				model.setMeta(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_META, builder.toString());
				model.setMeta(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_LOGICAL_META,
						String.valueOf(annotation.logical() == Logical.AND));
			}
		});
	}

	@Contribute(MetaDataLocator.class)
	public void contributeMetaDataLocator(final MappedConfiguration<String, String> configuration) {
		configuration.add(SecurityFilter.SHIRO_REQUIRES_AUTHENTICATION_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_USER_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_GUEST_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_ROLES_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_ROLES_LOGICAL_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_META, "");
		configuration.add(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_LOGICAL_META, "");
	}

	@Decorate(serviceInterface = RequestExceptionHandler.class)
	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final PageRenderLinkSource linkSource,
																   final Response response) {
		return new SecurityExceptionHandler(handler, linkSource, response, Security.class);
	}

	@Scope(ScopeConstants.PERTHREAD)
	public AccountContext buildAccountContext(@InjectService("accountService") final BaseEntityService<Account> accountService,
											  final HeroService heroService) {
		final Subject user = SecurityUtils.getSubject();
		final Long userId = user != null ? (Long) user.getPrincipal() : null;
		final Account account = accountService.query()
				.filter(account$.id$.eq(userId))
				.unique();
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
}
