package com.mutabra.web.services;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
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
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.domain.game.HeroLevel;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.battle.BattleService;
import com.mutabra.services.battle.BattleServiceImpl;
import com.mutabra.services.battle.ScriptEngine;
import com.mutabra.services.battle.ScriptEngineImpl;
import com.mutabra.services.battle.scripts.AttackScript;
import com.mutabra.services.battle.scripts.EffectScript;
import com.mutabra.services.battle.scripts.SummonScript;
import com.mutabra.services.game.HeroService;
import com.mutabra.services.game.HeroServiceImpl;
import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.internal.MailServiceImpl;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Symbol;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServicesModule {

    public static void bind(final ServiceBinder binder) {
        binder.bind(HeroService.class, HeroServiceImpl.class);
        binder.bind(BattleService.class, BattleServiceImpl.class);
        binder.bind(ScriptEngine.class, ScriptEngineImpl.class);
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

    public Datastore buildDatastore(final Set<Class> mappedClasses) throws UnknownHostException {
        final Mongo mongo = new Mongo();
        final Morphia morphia = new Morphia();
        for (Class mappedClass : mappedClasses) {
            morphia.map(mappedClass);
        }

        return morphia.createDatastore(mongo, "mutabra");
    }

    public MailService buildMailService(final @Symbol(ApplicationConstants.ROBOT_EMAIL) String robotEmail) {
        return new MailServiceImpl(robotEmail);
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

    @Contribute(ScriptEngine.class)
    public void contributeScriptEngine(final MappedConfiguration<EffectType, EffectScript> configuration) {
        configuration.addInstance(EffectType.MAGIC_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.MELEE_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.RANGED_ATTACK, AttackScript.class);
        configuration.addInstance(EffectType.SUMMON, SummonScript.class);
    }
}