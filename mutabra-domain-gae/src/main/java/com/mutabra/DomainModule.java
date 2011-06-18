package com.mutabra;

import com.mutabra.domain.Translation;
import com.mutabra.domain.TranslationImpl;
import com.mutabra.domain.common.*;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.*;
import org.greatage.domain.BaseFilterProcessor;
import org.greatage.domain.CompositeFilterProcessor;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.jdo.JdoExecutor;
import org.greatage.domain.jdo.JdoExecutorImpl;
import org.greatage.domain.jdo.JdoRepository;
import org.greatage.inject.Configuration;
import org.greatage.inject.MappedConfiguration;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Threaded;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DomainModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class);
		binder.bind(EntityRepository.class, JdoRepository.class);
		binder.bind(JdoExecutor.class, JdoExecutorImpl.class).withScope(Threaded.class);
	}

	@Build
	public PersistenceManagerFactory buildPersistenceManagerFactory() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional");
	}

	@Contribute(EntityFilterProcessor.class)
	public void contributeEntityFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
	}

	@Contribute(EntityRepository.class)
	public void contributeEntityRepository(final MappedConfiguration<Class, Class> configuration) {
		configuration.add(Translation.class, TranslationImpl.class);
		configuration.add(Account.class, AccountImpl.class);
		configuration.add(Role.class, RoleImpl.class);
		configuration.add(Permission.class, PermissionImpl.class);

		configuration.add(Face.class, FaceImpl.class);
		configuration.add(Race.class, RaceImpl.class);
		configuration.add(Level.class, LevelImpl.class);
		configuration.add(Card.class, CardImpl.class);
		configuration.add(Effect.class, EffectImpl.class);
		configuration.add(Summon.class, SummonImpl.class);

		configuration.add(Hero.class, HeroImpl.class);
		configuration.add(HeroCard.class, HeroCardImpl.class);
	}
}
