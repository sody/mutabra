package com.mutabra.web.services;

import com.mutabra.services.security.AnonymousProvider;
import com.mutabra.services.security.GameSecurityContext;
import com.mutabra.services.security.GameSecurityContextImpl;
import com.mutabra.services.security.UserProvider;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.*;
import org.greatage.security.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency(SecurityModule.class)
public class GameSecurityModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(ApplicationContext.class, ApplicationContextImpl.class).withScope(Threaded.class);
		binder.bind(GameSecurityContext.class, GameSecurityContextImpl.class).override();
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
}
