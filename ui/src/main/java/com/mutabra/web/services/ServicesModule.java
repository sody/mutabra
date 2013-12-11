/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

import org.apache.tapestry5.ioc.annotations.InjectService;
import org.mongodb.morphia.Datastore;
import com.mutabra.domain.common.*;
import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.battle.BattleService;
import com.mutabra.services.battle.BattleServiceImpl;
import com.mutabra.services.battle.ScriptEngine;
import com.mutabra.services.battle.ScriptEngineImpl;
import com.mutabra.services.battle.scripts.*;
import com.mutabra.services.game.HeroService;
import com.mutabra.services.game.HeroServiceImpl;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServicesModule {

    public static void bind(final ServiceBinder binder) {
        binder.bind(BattleService.class, BattleServiceImpl.class);
        binder.bind(ScriptEngine.class, ScriptEngineImpl.class);
    }

    public CodedEntityService<Level> buildLevelService(final Datastore datastore) {
        return new CodedEntityServiceImpl<Level>(datastore, Level.class);
    }

    public CodedEntityService<Face> buildFaceService(final Datastore datastore) {
        return new CodedEntityServiceImpl<Face>(datastore, Face.class);
    }

    public CodedEntityService<Race> buildRaceService(final Datastore datastore) {
        return new CodedEntityServiceImpl<Race>(datastore, Race.class);
    }

    public CodedEntityService<Card> buildCardService(final Datastore datastore) {
        return new CodedEntityServiceImpl<Card>(datastore, Card.class);
    }

    public BaseEntityService<Account> buildAccountService(final Datastore datastore) {
        return new BaseEntityServiceImpl<Account>(datastore, Account.class);
    }

    public HeroService buildHeroService(final Datastore datastore,
                                        final @InjectService("raceService") CodedEntityService<Race> raceService) {
        return new HeroServiceImpl(datastore, raceService);
    }

    @Contribute(ScriptEngine.class)
    public void contributeScriptEngine(final MappedConfiguration<EffectType, EffectScript> configuration) {
        configuration.addInstance(EffectType.CAST, CastScript.class);
        configuration.addInstance(EffectType.MAGIC_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.MELEE_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.RANGED_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.SUMMON, SummonScript.class);
        configuration.addInstance(EffectType.HEAL, HealScript.class);
        configuration.addInstance(EffectType.MOVE, MoveScript.class);
    }
}