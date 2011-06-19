package com.mutabra;

import com.mutabra.domain.Translation;
import com.mutabra.domain.TranslationImpl;
import com.mutabra.domain.common.*;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroCard;
import com.mutabra.domain.player.HeroCardImpl;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.*;
import org.datanucleus.store.appengine.jdo.DatastoreJDOPersistenceManagerFactory;
import org.greatage.domain.EntityRepository;
import org.greatage.inject.MappedConfiguration;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Dependency;

import javax.jdo.Constants;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency({JdoModule.class})
public class DomainModule {

	@Contribute(PersistenceManagerFactory.class)
	public void contributePersistenceManagerFactory(final MappedConfiguration<String, String> configuration) {
		configuration.add(Constants.PROPERTY_PERSISTENCE_MANAGER_FACTORY_CLASS,
				DatastoreJDOPersistenceManagerFactory.class.getName());
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
}
