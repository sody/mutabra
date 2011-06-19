package com.mutabra;

import org.greatage.domain.BaseFilterProcessor;
import org.greatage.domain.CompositeFilterProcessor;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.jdo.JdoExecutor;
import org.greatage.domain.jdo.JdoExecutorImpl;
import org.greatage.domain.jdo.JdoRepository;
import org.greatage.inject.Configuration;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Threaded;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(EntityRepository.class, JdoRepository.class);
		binder.bind(JdoExecutor.class, JdoExecutorImpl.class).withScope(Threaded.class);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class);
	}

	@Build
	public PersistenceManagerFactory buildPersistenceManagerFactory(final Map<String, String> jdoConfiguration) {
		return JDOHelper.getPersistenceManagerFactory(jdoConfiguration);
	}

	@Contribute(EntityFilterProcessor.class)
	public void contributeEntityFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
	}
}
