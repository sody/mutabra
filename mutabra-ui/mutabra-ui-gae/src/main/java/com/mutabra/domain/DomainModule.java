package com.mutabra.domain;

import com.mutabra.db.MutabraChangeLog;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardImpl;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectImpl;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.FaceImpl;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.LevelImpl;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.RaceImpl;
import com.mutabra.domain.common.Summon;
import com.mutabra.domain.common.SummonImpl;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;
import com.mutabra.domain.security.ChangeSet;
import com.mutabra.domain.security.ChangeSetImpl;
import com.mutabra.domain.security.Permission;
import com.mutabra.domain.security.PermissionImpl;
import com.mutabra.domain.security.Role;
import com.mutabra.domain.security.RoleImpl;
import com.mutabra.services.db.DatabaseService;
import com.mutabra.services.db.DefaultDatabaseService;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory;
import org.greatage.db.gae.GAEDatabase;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.jdo.JDOExecutor;
import org.greatage.domain.jdo.JDOExecutorImpl;
import org.greatage.domain.jdo.JDORepository;

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
		binder.bind(EntityRepository.class, JDORepository.class);
		binder.bind(JDOExecutor.class, JDOExecutorImpl.class).scope(ScopeConstants.PERTHREAD);
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
		configuration.add(ChangeSet.class, ChangeSetImpl.class);

		configuration.add(Face.class, FaceImpl.class);
		configuration.add(Race.class, RaceImpl.class);
		configuration.add(Level.class, LevelImpl.class);
		configuration.add(Card.class, CardImpl.class);
		configuration.add(Effect.class, EffectImpl.class);
		configuration.add(Summon.class, SummonImpl.class);

		configuration.add(Hero.class, HeroImpl.class);
		configuration.add(HeroCard.class, HeroCardImpl.class);
	}

	@Startup
	public void updateDatabase(final DatabaseService databaseService,
							   final @Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode) {
		if (!productionMode) {
			databaseService.update(false, false);
		}
	}
}
