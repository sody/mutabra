package com.mutabra.web.services;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoURI;
import com.mutabra.domain.battle.*;
import com.mutabra.domain.common.*;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroLevel;
import com.mutabra.web.ApplicationConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DomainModule {

    @FactoryDefaults
    @Contribute(SymbolProvider.class)
    public void contributeFactoryDefaults(final MappedConfiguration<String, String> configuration) {
        configuration.add(ApplicationConstants.MONGO_URI, "${env.mongohq_url}");
    }

    @Contribute(Datastore.class)
    public void contributeDatastore(final Configuration<Class> configuration) {
        configuration.add(Ability.class);
        configuration.add(Card.class);
        configuration.add(Effect.class);
        configuration.add(Face.class);
        configuration.add(Level.class);
        configuration.add(Race.class);
        configuration.add(Account.class);
        configuration.add(Hero.class);
        configuration.add(HeroAppearance.class);
        configuration.add(HeroLevel.class);
        configuration.add(Battle.class);
        configuration.add(BattleAbility.class);
        configuration.add(BattleCard.class);
        configuration.add(BattleCreature.class);
        configuration.add(BattleEffect.class);
        configuration.add(BattleHero.class);
        configuration.add(BattleTarget.class);
    }

    public Datastore buildDatastore(final Collection<Class> mappedClasses,
                                    @Symbol(ApplicationConstants.MONGO_URI) final String mongoUri) throws UnknownHostException {
        final MongoURI uri = new MongoURI(mongoUri);

        final Morphia morphia = new Morphia();
        for (Class mappedClass : mappedClasses) {
            morphia.map(mappedClass);
        }

        return morphia.createDatastore(uri.connect(), uri.getDatabase(), uri.getUsername(), uri.getPassword());
    }
}
