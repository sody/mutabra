package com.noname.web.services;

import com.noname.services.BaseEntityService;
import com.noname.services.security.AnonymousProvider;
import com.noname.services.security.GameSecurityContext;
import com.noname.services.security.GameSecurityContextImpl;
import com.noname.services.security.UserProvider;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency(SecurityModule.class)
public class GameSecurityModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ApplicationContext.class, ApplicationContextImpl.class).withScope(ScopeConstants.THREAD);
		binder.bind(GameSecurityContext.class, GameSecurityContextImpl.class).withId(SecurityContext.class.getName()).override();
	}

	@Build
	public PasswordEncoder buildPasswordEncoder() {
		return new MessageDigestPasswordEncoder("MD5", false);
	}

	@Contribute(AuthenticationManager.class)
	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration) {
		configuration.addInstance(AnonymousProvider.class, "anonymous");
		configuration.addInstance(UserProvider.class, "password");
	}

	@Intercept(BaseEntityService.class)
	@Order(value = "Security", constraints = "before:Transaction")
	public MethodAdvice interceptServices(final GameSecurityContext securityContext) {
		return new AuthoritySecurityAdvice(securityContext);
	}
}
