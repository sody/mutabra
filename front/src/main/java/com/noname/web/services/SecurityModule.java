package com.noname.web.services;

import com.noname.web.pages.security.SignIn;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.*;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.greatage.security.*;
import org.greatage.security.drools.DroolsAccessControlManager;
import org.greatage.tapestry.internal.SecuredAnnotationWorker;
import org.greatage.tapestry.internal.SecurityExceptionHandler;
import org.greatage.tapestry.internal.UserPersistenceFilter;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(AuthenticationManager.class, AuthenticationManagerImpl.class);
		binder.bind(SecurityChecker.class, SecurityCheckerImpl.class);
		binder.bind(UserContext.class, UserContextImpl.class).scope(ScopeConstants.PERTHREAD);
		binder.bind(AccessControlManager.class, DroolsAccessControlManager.class);
		binder.bind(SecurityService.class, SecurityService.class);
	}

	public KnowledgeBase buildKnowledgeBase(
			final Collection<String> resources,
			final AssetSource assetSource) throws IOException {
		final KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		for (String resource : resources) {
			final Resource res = assetSource.resourceForPath(resource);
			builder.add(ResourceFactory.newInputStreamResource(res.openStream()), ResourceType.DRL);
		}

		if (builder.hasErrors()) {
			final StringBuilder sb = new StringBuilder();
			for (KnowledgeBuilderError error : builder.getErrors()) {
				sb.append("Error at lines ").append(Arrays.toString(error.getErrorLines())).append(":").append("\n");
				sb.append(error.getMessage()).append("\n");
			}
			throw new RuntimeException(sb.toString());
		}
		final KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		return knowledgeBase;
	}

	public void contributeKnowledgeBase(final org.apache.tapestry5.ioc.Configuration<String> configuration) {
		configuration.add("classpath:security/sample-security-rules.drl");
	}

	public PasswordEncoder buildPasswordEncoder() {
		return new MessageDigestPasswordEncoder("MD5", false);
	}

	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration) {
		configuration.addInstance("default", GameUserProvider.class);
	}


	public void contributeComponentClassTransformWorker(
			final OrderedConfiguration<ComponentClassTransformWorker> configuration) {
		configuration.addInstance("PageSecurity", SecuredAnnotationWorker.class, "before:OnEvent");
	}

	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final ResponseRenderer renderer,
																   final ComponentClassResolver resolver,
																   final Logger logger) {
		return new SecurityExceptionHandler(handler, renderer, resolver, SignIn.class, logger);
	}

	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
		configuration.addInstance("SecurityContextInitializer", UserPersistenceFilter.class);
	}
}
