package com.mutabra.web.services;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.game.*;
import com.mutabra.security.*;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.battle.BattleService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.SecurityConstants;
import com.mutabra.web.internal.security.*;
import com.mutabra.web.pages.Security;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
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
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.meta.FixedExtractor;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

    @FactoryDefaults
    @Contribute(SymbolProvider.class)
    public void setupDefaultValues(final MappedConfiguration<String, String> configuration) {
        // we can override all this values with system properties and servlet context parameters

        // hash service constants should be retrieved from environment values by default
        configuration.add(SecurityConstants.HASH_ALGORITHM, "${env.hash_algorithm}");
        configuration.add(SecurityConstants.HASH_ITERATIONS, "${env.hash_iterations}");
        configuration.add(SecurityConstants.HASH_PRIVATE_SALT, "${env.hash_private_salt}");

        // add default value for token expiration time
        configuration.add(SecurityConstants.TOKEN_EXPIRATION_TIME, "86400000");

        // created here https://developers.facebook.com/apps
        configuration.add(SecurityConstants.FACEBOOK_KEY, "${env.facebook_id}");
        configuration.add(SecurityConstants.FACEBOOK_SECRET, "${env.facebook_secret}");
        // created here https://dev.twitter.com/apps/new
        configuration.add(SecurityConstants.TWITTER_KEY, "${env.twitter_id}");
        configuration.add(SecurityConstants.TWITTER_SECRET, "${env.twitter_secret}");
        // created here https://code.google.com/apis/console
        configuration.add(SecurityConstants.GOOGLE_KEY, "${env.google_id}");
        configuration.add(SecurityConstants.GOOGLE_SECRET, "${env.google_secret}");
        // created here https://vk.com/editapp?act=create
        configuration.add(SecurityConstants.VK_KEY, "${env.vk_id}");
        configuration.add(SecurityConstants.VK_SECRET, "${env.vk_secret}");
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
        configuration.add("oauth", new OAuthRealm(accountService, generator));
        configuration.add("confirmation", new ConfirmationRealm(accountService));
    }

    public HashedPasswordMatcher buildHashedPasswordMatcher(@Symbol(SecurityConstants.HASH_ALGORITHM) final String hashAlgorithmName,
                                                            @Symbol(SecurityConstants.HASH_ITERATIONS) final int hashIterations,
                                                            @Symbol(SecurityConstants.HASH_PRIVATE_SALT) final String privateSalt,
                                                            @Symbol(SecurityConstants.TOKEN_EXPIRATION_TIME) final long tokenExpirationTime) {

        final DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName(hashAlgorithmName);
        hashService.setHashIterations(hashIterations);
        hashService.setPrivateSalt(ByteSource.Util.bytes(Base64.decode(privateSalt)));
        hashService.setGeneratePublicSalt(true);

        return new HashedPasswordMatcher(hashService, tokenExpirationTime);
    }

    public OAuth2 buildFacebookService(@Symbol(SecurityConstants.FACEBOOK_KEY) final String clientId,
                                       @Symbol(SecurityConstants.FACEBOOK_SECRET) final String clientSecret,
                                       final PageRenderLinkSource linkSource) {
        final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".facebook:connected";
        return new Facebook(clientId, clientSecret, redirectUri);
    }

    public OAuth buildTwitterService(@Symbol(SecurityConstants.TWITTER_KEY) final String consumerKey,
                                     @Symbol(SecurityConstants.TWITTER_SECRET) final String consumerSecret,
                                     final PageRenderLinkSource linkSource) {
        final String callbackUrl = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".twitter:connected";
        return new Twitter(consumerKey, consumerSecret, callbackUrl);
    }

    public OAuth2 buildGoogleService(@Symbol(SecurityConstants.GOOGLE_KEY) final String consumerKey,
                                     @Symbol(SecurityConstants.GOOGLE_SECRET) final String consumerSecret,
                                     final PageRenderLinkSource linkSource) {
        final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".google:connected";
        return new Google(consumerKey, consumerSecret, redirectUri);
    }

    public OAuth2 buildVkService(@Symbol(SecurityConstants.VK_KEY) final String consumerKey,
                                 @Symbol(SecurityConstants.VK_SECRET) final String consumerSecret,
                                 final PageRenderLinkSource linkSource) {
        final String redirectUri = linkSource.createPageRenderLink(Security.class).toAbsoluteURI() + ".vk:connected";
        return new VKontakte(consumerKey, consumerSecret, redirectUri);
    }

    @Contribute(HttpServletRequestHandler.class)
    public void contributeHttpServletRequestHandler(final OrderedConfiguration<HttpServletRequestFilter> configuration) {
        configuration.addInstance("Shiro", SecurityRequestFilter.class);
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

    @Contribute(ComponentClassTransformWorker2.class)
    public static void contributeComponentClassTransformWorker2(final OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
        configuration.addInstance("SecurityWorker", SecurityWorker.class, "after:OnEvent");
    }

    @Decorate(serviceInterface = RequestExceptionHandler.class)
    public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
                                                                   final RequestGlobals globals,
                                                                   final PageRenderLinkSource linkSource,
                                                                   final ComponentClassResolver resolver) {

        final String loginPage = resolver.resolvePageClassNameToPageName(Security.class.getName());
        return new SecurityExceptionHandler(handler, globals, linkSource, loginPage);
    }

    @Scope(ScopeConstants.PERTHREAD)
    public AccountContext buildAccountContext(@InjectService("accountService")
                                              final BaseEntityService<Account> accountService,
                                              final HeroService heroService,
                                              final BattleService battleService) {
        final Subject user = SecurityUtils.getSubject();
        final ObjectId userId = user != null ? (ObjectId) user.getPrincipal() : null;
        final Account account = accountService.get(userId);

        return new AccountContext() {
            private Hero hero;
            private Battle battle;

            public Account getAccount() {
                return account;
            }

            public Hero getHero() {
                if (hero == null && account != null) {
                    hero = heroService.get(account);
                }
                return hero;
            }

            public Battle getBattle() {
                if (battle == null && account != null) {
                    battle = battleService.get(account);
                }
                return battle;
            }
        };
    }

    @Startup
    public void createAdminAccount(@InjectService("accountService") final BaseEntityService<Account> accountService,
                                   final HashedPasswordMatcher generator) {

        if (accountService.query().countAll() <= 0) {
            final Account account = new Account();
            account.setName("admin");
            account.setRole(Role.ADMIN);
            account.setRegistered(new Date());

            final AccountCredential emailCredential = new AccountCredential();
            emailCredential.setType(AccountCredentialType.EMAIL);
            emailCredential.setKey("admin");
            final Hash hash = generator.generateHash("admin");
            emailCredential.setSecret(hash.toBase64());
            emailCredential.setSalt(hash.getSalt().toBase64());
            account.getCredentials().add(emailCredential);

            accountService.save(account);
        }
    }
}
