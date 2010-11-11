package com.noname.web.services;

import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.*;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.compiler.DroolsParserException;
import org.drools.io.ResourceFactory;
import org.greatage.security.acl.AccessControlManager;
import org.greatage.security.acl.drools.DroolsAccessControlManager;
import org.greatage.security.auth.*;
import org.greatage.security.context.PermissionResolver;
import org.greatage.security.context.PermissionResolverImpl;
import org.greatage.security.context.UserContext;
import org.greatage.security.context.UserContextImpl;
import org.greatage.tapestry.internal.SecuredAnnotationWorker;
import org.greatage.tapestry.internal.SecurityExceptionHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {
	private static final String SECURITY_ENCODE_ALGORITHM = "security.encode-algorithm";
	private static final String SECURITY_KNOWLEDGE_DIRECTORY = "security.knowledge-directory";

	public static void bind(final ServiceBinder binder) {
		binder.bind(AuthenticationManager.class, AuthenticationManagerImpl.class);
		binder.bind(PermissionResolver.class, PermissionResolverImpl.class);
		binder.bind(AccessControlManager.class, DroolsAccessControlManager.class);
		binder.bind(UserContext.class, UserContextImpl.class).scope(ScopeConstants.PERTHREAD);
	}

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SECURITY_ENCODE_ALGORITHM, "MD5");
		configuration.add(SECURITY_KNOWLEDGE_DIRECTORY, "security");
	}

	public KnowledgeBuilderConfiguration buildKnowledgeBuilderConfiguration(final Map<String, String> knowledgeBuilderProperties) {
		final KnowledgeBuilderConfiguration configuration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		for (Map.Entry<String, String> entry : knowledgeBuilderProperties.entrySet()) {
			configuration.setProperty(entry.getKey(), entry.getValue());
		}
		return configuration;
	}

	public KnowledgeBase buildKnowledgeBase(
			final Collection<String> resources,
			final KnowledgeBuilderConfiguration configuration,
			final AssetSource assetSource) throws DroolsParserException, IOException {
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
			throw new DroolsParserException(sb.toString());
		}
		final KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
		return knowledgeBase;
	}

	public void contributeKnowledgeBase(final Configuration<String> configuration) {
		configuration.add("classpath:security/sample-security-rules.drl");
	}

	public PasswordEncoder buildPasswordEncoder(@Symbol(SECURITY_ENCODE_ALGORITHM) final String algorithm) {
		return new MessageDigestPasswordEncoder(algorithm, false);
	}

	public void contributeAuthenticationManager(final OrderedConfiguration<AuthenticationProvider> configuration) {
		configuration.addInstance("default", GameUserProvider.class);
	}

	public void contributeComponentClassTransformWorker(
			final OrderedConfiguration<ComponentClassTransformWorker> configuration) {
		configuration.addInstance("PageSecurity", SecuredAnnotationWorker.class, "before:OnEvent");
	}

	public RequestExceptionHandler decorateRequestExceptionHandler(final RequestExceptionHandler handler,
																   final ResponseRenderer renderer) {
		return new SecurityExceptionHandler(handler, renderer);
	}

	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration) {
		configuration.addInstance("SecurityContextInitializer", UserPersistenceFilter.class);
	}
}
