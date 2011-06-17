package com.mutabra.web.services;

import com.mutabra.DomainModule;
import com.mutabra.ServicesModule;
import com.mutabra.services.security.AnonymousProvider;
import com.mutabra.services.security.GameSecurityContext;
import com.mutabra.services.security.GameSecurityContextImpl;
import com.mutabra.services.security.UserProvider;
import com.mutabra.web.services.i18n.Translator;
import com.mutabra.web.services.i18n.TranslatorImpl;
import org.greatage.domain.hibernate.HibernateModule;
import org.greatage.inject.OrderedConfiguration;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Dependency;
import org.greatage.inject.annotations.Threaded;
import org.greatage.security.AuthenticationManager;
import org.greatage.security.AuthenticationManagerImpl;
import org.greatage.security.AuthenticationProvider;
import org.greatage.security.MessageDigestPasswordEncoder;
import org.greatage.security.PasswordEncoder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency({DomainModule.class, ServicesModule.class})
public class MainModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(AuthenticationManager.class, AuthenticationManagerImpl.class);
		binder.bind(ApplicationContext.class, ApplicationContextImpl.class).withScope(Threaded.class);
		binder.bind(GameSecurityContext.class, GameSecurityContextImpl.class).override();

		binder.bind(Translator.class, TranslatorImpl.class);
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