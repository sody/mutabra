package com.noname.web.services;

import com.noname.services.BaseEntityService;
import com.noname.services.security.UserProvider;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.ScopeConstants;
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
	}

	@Build
	public PasswordEncoder buildPasswordEncoder() {
		return new MessageDigestPasswordEncoder("MD5", false);
	}

	@Contribute(AuthenticationManager.class)
	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration) {
		configuration.addInstance(UserProvider.class, "default");
	}

	@Intercept(BaseEntityService.class)
	@Order(value = "Security", constraints = "before:Transaction")
	public MethodAdvice interceptServices(@Inject("AuthoritySecurityChecker") final SecurityChecker checker) {
		return new AuthoritySecurityAdvice(checker);
	}
}
