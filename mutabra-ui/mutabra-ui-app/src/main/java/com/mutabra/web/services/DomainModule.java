package com.mutabra.web.services;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.mutabra.db.GAEDatabaseService;
import com.mutabra.db.MutabraChangeLog;
import com.mutabra.domain.Keys;
import com.mutabra.domain.Translation;
import com.mutabra.domain.TranslationImpl;
import com.mutabra.domain.common.*;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.*;
import com.mutabra.services.CodedEntityFilterProcessor;
import com.mutabra.services.TranslationFilterProcessor;
import com.mutabra.services.security.AccountFilterProcessor;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory;
import org.greatage.db.DatabaseService;
import org.greatage.db.DefaultDatabaseService;
import org.greatage.db.gae.GAEDatabase;
import org.greatage.domain.BaseFilterProcessor;
import org.greatage.domain.CompositeFilterProcessor;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.jdo.JdoExecutor;
import org.greatage.domain.jdo.JdoExecutorImpl;
import org.greatage.domain.jdo.JdoRepository;

import javax.jdo.Constants;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DomainModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(EntityRepository.class, JdoRepository.class);
		binder.bind(JdoExecutor.class, JdoExecutorImpl.class).scope(ScopeConstants.PERTHREAD);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class);
	}

	public DatabaseService buildDatabaseService() {
		return new DefaultDatabaseService(new GAEDatabase(), new MutabraChangeLog());
	}

	public PersistenceManagerFactory buildPersistenceManagerFactory(final Map<String, String> jdoConfiguration) {
		final PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory(jdoConfiguration);
		Keys.init(factory);
		return factory;
	}

	@Contribute(PersistenceManagerFactory.class)
	public void contributePersistenceManagerFactory(final MappedConfiguration<String, String> configuration) {
		configuration.add(Constants.PROPERTY_PERSISTENCE_MANAGER_FACTORY_CLASS, DatastoreJDOPersistenceManagerFactory.class.getName());
		configuration.add(Constants.PROPERTY_CONNECTION_URL, "appengine");
		configuration.add(Constants.OPTION_NONTRANSACTIONAL_READ, "true");
		configuration.add(Constants.OPTION_NONTRANSACTIONAL_WRITE, "true");
		configuration.add(Constants.OPTION_RETAIN_VALUES, "true");
		configuration.add("datanucleus.appengine.autoCreateDatastoreTxns", "true");
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

	@Contribute(EntityFilterProcessor.class)
	public void contributeEntityFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
		configuration.addInstance(CodedEntityFilterProcessor.class);
		configuration.addInstance(TranslationFilterProcessor.class);
		configuration.addInstance(AccountFilterProcessor.class);
	}

	@Startup
	public void updateDatabase(final DatabaseService databaseService) {
		databaseService.update();
	}
}
