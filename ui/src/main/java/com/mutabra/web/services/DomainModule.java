/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleTarget;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
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
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

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

    public MongoClient buildMongoClient(@Symbol(ApplicationConstants.MONGO_URI) final String mongoUri) throws UnknownHostException {
        final MongoClientURI uri = new MongoClientURI(mongoUri);
        return new MongoClient(uri);
    }

    public Datastore buildDatastore(final Collection<Class> mappedClasses,
                                    final MongoClient mongoClient,
                                    @Symbol(ApplicationConstants.MONGO_URI) final String mongoUri) throws UnknownHostException {
        final Morphia morphia = new Morphia();
        for (Class mappedClass : mappedClasses) {
            morphia.map(mappedClass);
        }

        final MongoClientURI uri = new MongoClientURI(mongoUri);
        return morphia.createDatastore(mongoClient, uri.getDatabase());
    }
}
