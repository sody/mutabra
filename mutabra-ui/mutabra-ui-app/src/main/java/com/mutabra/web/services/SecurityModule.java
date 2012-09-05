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
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.internal.security.MainRealm;
import com.mutabra.web.internal.security.SecurityExceptionHandler;
import com.mutabra.web.internal.security.SecurityFilter;
import com.mutabra.web.internal.security.SecurityRequestFilter;
import com.mutabra.web.pages.Security;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.PropertyShadowBuilder;
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
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;

import java.util.Date;
import java.util.List;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {
	private static final String SECURITY_HASH_ALGORITHM = "security.hash-algorithm";
	private static final String SECURITY_HASH_ITERATIONS = "security.hash-iterations";
	private static final String SECURITY_PRIVATE_SALT = "security.private-salt";

	public static void bind(final ServiceBinder binder) {
		binder.bind(WebSessionManager.class, ServletContainerSessionManager.class);
	}

	@ApplicationDefaults
	@Contribute(SymbolProvider.class)
	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SECURITY_HASH_ALGORITHM, Sha512Hash.ALGORITHM_NAME);
		configuration.add(SECURITY_HASH_ITERATIONS, "512");
		configuration.add(SECURITY_PRIVATE_SALT, "8carxXOr0uNa8aqhCYZZZA==");
	}

	@Scope(ScopeConstants.PERTHREAD)
	public AccountContext buildAccountContext(@InjectService("accountService") final BaseEntityService<Account> accountService,
											  final HeroService heroService) {
		final Subject user = SecurityUtils.getSubject();
		final String username = user != null ? (String) user.getPrincipal() : null;
		final Account account = username == null ? null : Authorities.isTwitterUser(username) ?
				accountService.query()
						.filter(account$.twitterUser.eq(Authorities.getTwitterUser(username)))
						.unique() :
				accountService.query()
						.filter(account$.email.eq(username))
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

	public Account buildAccount(final AccountContext accountContext, final PropertyShadowBuilder shadowBuilder) {
		return shadowBuilder.build(accountContext, "account", Account.class);
	}

	public Hero buildHero(final AccountContext accountContext, final PropertyShadowBuilder shadowBuilder) {
		return shadowBuilder.build(accountContext, "hero", Hero.class);
	}

	public HashService buildHashService(@Symbol(SECURITY_HASH_ALGORITHM) final String hashAlgorithmName,
										@Symbol(SECURITY_HASH_ITERATIONS) final int hashIterations,
										@Symbol(SECURITY_PRIVATE_SALT) final String privateSalt) {

		final DefaultHashService hashService = new DefaultHashService();
		hashService.setHashAlgorithmName(hashAlgorithmName);
		hashService.setHashIterations(hashIterations);
		hashService.setPrivateSalt(ByteSource.Util.bytes(Base64.decode(privateSalt)));
		hashService.setGeneratePublicSalt(true);

		return hashService;
	}

	public HashingPasswordService buildPasswordService(final HashService hashService) {
		final DefaultPasswordService passwordService = new DefaultPasswordService();
		passwordService.setHashService(hashService);
		return passwordService;
	}

	public CredentialsMatcher buildCredentialsMatcher(final PasswordService passwordService) {
		final PasswordMatcher passwordMatcher = new PasswordMatcher() {
			@Override
			protected Object getStoredPassword(final AuthenticationInfo storedAccountInfo) {
				return storedAccountInfo == null ? null : new SimpleHash(null) {
					{
						final Object credentials = storedAccountInfo.getCredentials();
						byte[] sourceBytes = toBytes(credentials);
						if (credentials instanceof String || credentials instanceof char[]) {
							sourceBytes = Base64.decode(sourceBytes);
						}
						setBytes(sourceBytes);
						if (storedAccountInfo instanceof SaltedAuthenticationInfo) {
							setSalt(((SaltedAuthenticationInfo) storedAccountInfo).getCredentialsSalt());
						}
					}

					@Override
					public int getIterations() {
						return 0;
					}
				};
			}
		};
		passwordMatcher.setPasswordService(passwordService);
		return passwordMatcher;
	}

	public WebSecurityManager buildWebSecurityManager(final List<Realm> realms) {
		return new DefaultWebSecurityManager(realms);
	}

	public WebEnvironment buildWebEnvironment(final ApplicationGlobals applicationGlobals,
											  final WebSecurityManager securityManager) {
		final DefaultWebEnvironment environment = new DefaultWebEnvironment();
		environment.setServletContext(applicationGlobals.getServletContext());
		environment.setWebSecurityManager(securityManager);
		return environment;
	}

	@Contribute(WebSecurityManager.class)
	public void contributeWebSecurityManager(final OrderedConfiguration<Realm> configuration,
											 @InjectService("accountService") final BaseEntityService<Account> accountService,
											 final CredentialsMatcher credentialsMatcher,
											 final HashingPasswordService passwordService) {
		configuration.add("main", new MainRealm(credentialsMatcher, accountService));

		if (accountService.query().count() <= 0) {
			final Account account = accountService.create();
			account.setEmail("admin@mutabra.com");
			account.setName("admin");
			account.setRole(Role.ADMIN);
			account.setRegistered(new Date());

			final Hash hash = passwordService.hashPassword("admin");
			account.setPassword(hash.toBase64());
			account.setSalt(hash.getSalt().toBase64());

			accountService.save(account);
		}
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
		configuration.add(RequiresAuthentication.class, new MetaDataExtractor<RequiresAuthentication>() {
			public void extractMetaData(final MutableComponentModel model, final RequiresAuthentication annotation) {
				if (model.isPage()) {
					model.setMeta(Authorities.SHIRO_REQUIRES_AUTHENTICATION_META, Boolean.TRUE.toString());
				}
			}
		});
		configuration.add(RequiresUser.class, new MetaDataExtractor<RequiresUser>() {
			public void extractMetaData(final MutableComponentModel model, final RequiresUser annotation) {
				if (model.isPage()) {
					model.setMeta(Authorities.SHIRO_REQUIRES_USER_META, Boolean.TRUE.toString());
				}
			}
		});
		configuration.add(RequiresGuest.class, new MetaDataExtractor<RequiresGuest>() {
			public void extractMetaData(final MutableComponentModel model, final RequiresGuest annotation) {
				if (model.isPage()) {
					model.setMeta(Authorities.SHIRO_REQUIRES_GUEST_META, Boolean.TRUE.toString());
				}
			}
		});
	}

	@Contribute(MetaDataLocator.class)
	public void contributeMetaDataLocator(final MappedConfiguration<String, String> configuration) {
		configuration.add(Authorities.SHIRO_REQUIRES_AUTHENTICATION_META, "");
		configuration.add(Authorities.SHIRO_REQUIRES_USER_META, "");
		configuration.add(Authorities.SHIRO_REQUIRES_GUEST_META, "");
	}

	@Decorate(serviceInterface = RequestExceptionHandler.class)
	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final PageRenderLinkSource linkSource,
																   final Response response) {
		return new SecurityExceptionHandler(handler, linkSource, response, Security.class);
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
