package com.noname.web.services;

import com.noname.services.BaseEntityService;
import com.noname.web.services.security.GameUserProvider;
import org.drools.KnowledgeBase;
import org.greatage.ioc.Configuration;
import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.annotations.*;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.*;
import org.greatage.security.drools.DroolsSecurityModule;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency(DroolsSecurityModule.class)
public class SecurityModule {

	@Configure(KnowledgeBase.class)
	public void configureKnowledgeBase(final Configuration<String> configuration) {
		configuration.add("security/sample-security-rules.drl");
	}

	@Build
	public PasswordEncoder buildPasswordEncoder() {
		return new MessageDigestPasswordEncoder("MD5", false);
	}

	@Configure(AuthenticationManager.class)
	public void configureAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration) {
		configuration.addInstance(GameUserProvider.class, "default");
	}

	@Intercept(BaseEntityService.class)
	@Order(value = "Security", constraints = "before:Transaction")
	public MethodAdvice interceptServices(final SecurityChecker checker) {
		return new SecurityAdvice(checker);
	}
}
