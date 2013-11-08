/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle;

import org.mongodb.morphia.Datastore;
import com.mutabra.domain.battle.*;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountHero;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleServiceImpl
        extends BaseEntityServiceImpl<Battle>
        implements BattleService {

    private final ScriptEngine scriptEngine;

    public BattleServiceImpl(final Datastore datastore, final ScriptEngine scriptEngine) {
        super(datastore, Battle.class);

        this.scriptEngine = scriptEngine;
    }

    public Battle get(final Account account) {
        final AccountHero accountHero = account.getHero();
        return accountHero != null ?
                query().filter("heroes.id =", accountHero.getId()).get() :
                null;
    }

    public void create(final Hero hero) {
        final Battle battle = new Battle();
        battle.setStartedAt(new Date());
        battle.setActive(false);

        // init hero
        final BattleHero battleHero = new BattleHero(hero.getId());
        fillHero(battleHero, hero);
        battleHero.setReady(true);
        battle.getHeroes().add(battleHero);

        save(battle);
    }

    public void apply(final Battle battle, final Hero hero) {
        // init hero
        final BattleHero battleHero = new BattleHero(hero.getId());
        fillHero(battleHero, hero);
        battleHero.setReady(true);
        battle.getHeroes().add(battleHero);

        // start battle
        start(battle);

        save(battle);
    }

    public List<Battle> findBattles(final long expirationTime) {
        return query()
                .filter("active =", false)
                .filter("startedAt >", new Date(System.currentTimeMillis() - expirationTime))
                .order("-startedAt")
                .limit(20)
                .asList();
    }

    public void cast(final Battle battle,
                     final BattleCard card,
                     final BattleTarget target) {
        final BattleHero hero = card.getHero();

        // add cast effect
        // it will subtract blood cost from hero health
        final BattleEffect castEffect = new BattleEffect();
        castEffect.setCode(card.getCode());
        castEffect.setDuration(1);
        castEffect.setPower(card.getBloodCost());
        castEffect.setType(EffectType.CAST);
        castEffect.setTargetType(TargetType.NOBODY);
        castEffect.getCaster().setHero(hero);
        castEffect.getTarget().setCard(card);
        battle.getEffects().add(castEffect);

        // add remaining card effects
        for (Effect effect : card.getEffects()) {
            final BattleEffect battleEffect = new BattleEffect();
            fillEffect(battleEffect, effect);

            battleEffect.getTarget().setPosition(target.getPosition());
            battleEffect.getTarget().setSide(target.getSide());
            battleEffect.getCaster().setHero(hero);

            battle.getEffects().add(battleEffect);
        }

        // set hero exhausted
        hero.setReady(true);

        // end round if ready
        if (battle.isAllReady()) {
            endRound(battle);
        }

        // check whether battle is over
        if (!battle.isActive()) {
            end(battle);
            delete(battle);
        } else {
            save(battle);
        }
    }

    public void cast(final Battle battle,
                     final BattleAbility ability,
                     final BattleTarget target) {
        final BattleCreature creature = ability.getCreature();

        // add cast effect
        // it will subtract blood cost from creature health
        final BattleEffect castEffect = new BattleEffect();
        castEffect.setCode(ability.getCode());
        castEffect.setDuration(1);
        castEffect.setPower(ability.getBloodCost());
        castEffect.setType(EffectType.CAST);
        castEffect.setTargetType(TargetType.NOBODY);
        castEffect.getCaster().setCreature(creature);
        castEffect.getTarget().setAbility(ability);
        battle.getEffects().add(castEffect);

        // add remaining ability effects
        for (Effect effect : ability.getEffects()) {
            final BattleEffect battleEffect = new BattleEffect();
            fillEffect(battleEffect, effect);

            battleEffect.getCaster().setCreature(creature);
            battleEffect.getTarget().setPosition(target.getPosition());
            battleEffect.getTarget().setSide(target.getSide());

            battle.getEffects().add(battleEffect);
        }
        // set creature exhausted
        creature.setReady(true);

        // end round if ready
        if (battle.isAllReady()) {
            endRound(battle);
        }

        // check whether battle is over
        if (!battle.isActive()) {
            end(battle);
            delete(battle);
        } else {
            save(battle);
        }
    }

    public void skip(final Battle battle, final BattleHero hero) {
        hero.setReady(true);

        for (BattleCreature battleCreature : hero.getCreatures()) {
            battleCreature.setReady(true);
        }

        // end round if ready
        if (battle.isAllReady()) {
            endRound(battle);
        }

        // check whether battle is over
        if (!battle.isActive()) {
            end(battle);
            delete(battle);
        } else {
            save(battle);
        }
    }

    private void start(final Battle battle) {
        battle.setActive(true);
        battle.setStartedAt(new Date());
        battle.setRound(1);

        for (BattleHero battleHero : battle.getHeroes()) {
            final Hero hero = datastore().get(Hero.class, battleHero.getId());
            fillHero(battleHero, hero);

            // the initial hero position is center
            battleHero.setPosition(new BattlePosition(1, 0));
            battleHero.setReady(false);

            // init hero cards
            final List<Card> cards = datastore().get(Card.class, hero.getCards()).asList();
            // shuffle deck
            Collections.shuffle(cards);
            // init card copies for battle
            for (Card card : cards) {
                final BattleCard battleCard = new BattleCard();
                fillCard(battleCard, card);

                battleCard.setType(BattleCardType.DECK);
                battleHero.getCards().add(battleCard);
            }
            // get 3 first cards from deck to hand
            dealCards(battleHero, 3);
        }
    }

    private void end(final Battle battle) {
        for (BattleHero battleHero : battle.getHeroes()) {
            final Hero hero = datastore().get(Hero.class, battleHero.getId());
            // update hero rating
            hero.getLevel().setRating(battleHero.getLevel().getRating());
            // TODO: add level-up logic
            datastore().save(hero);
        }

        delete(battle);
    }

    private void endRound(final Battle battle) {
        // process effects
        scriptEngine.executeScripts(battle);

        // continue only if battle is still active
        if (battle.isActive()) {
            //start new round
            battle.setRound(battle.getRound() + 1);
            for (BattleHero battleHero : battle.getHeroes()) {
                // increase mental power each round
                battleHero.setMentalPower(battleHero.getMentalPower() + 1);
                // move one card from deck to hand
                dealCards(battleHero, 1);
                // each hero should be able to cast cards in the beginning of the round
                // except the situation when there are no cards in the hand
                battleHero.setReady(battleHero.getHand().isEmpty());

                for (BattleCreature battleCreature : battleHero.getCreatures()) {
                    // each creature should be able to use abilities in the beginning of the round
                    // except the situation when there are no abilities
                    battleCreature.setReady(battleCreature.getAbilities().isEmpty());
                }
            }

            // there are no more cards
            if (battle.isAllReady()) {
                battle.setActive(false);
            }
        }
    }

    private void dealCards(final BattleHero battleHero, final int count) {
        int i = 0;
        final Iterator<BattleCard> iterator = battleHero.getDeck().iterator();
        while (iterator.hasNext() && i < count) {
            iterator.next().setType(BattleCardType.HAND);
            i++;
        }
    }

    private void fillHero(final BattleHero battleHero, final Hero hero) {
        battleHero.setHealth(hero.getHealth());
        battleHero.setMentalPower(hero.getMentalPower());

        battleHero.getLevel().setCode(hero.getLevel().getCode());
        battleHero.getLevel().setRating(hero.getLevel().getRating());
        battleHero.getLevel().setNextLevelRating(hero.getLevel().getNextLevelRating());

        battleHero.getAppearance().setName(hero.getAppearance().getName());
        battleHero.getAppearance().setRace(hero.getAppearance().getRace());
        battleHero.getAppearance().setFace(hero.getAppearance().getFace());
    }

    private void fillCard(final BattleCard battleCard, final Card card) {
        battleCard.setCode(card.getCode());
        battleCard.setTargetType(card.getTargetType());
        battleCard.setBloodCost(card.getBloodCost());
        battleCard.getEffects().addAll(card.getEffects());
    }

    private void fillEffect(final BattleEffect battleEffect, final Effect effect) {
        battleEffect.setCode(effect.getCode());
        battleEffect.setDuration(effect.getDuration());
        battleEffect.setHealth(effect.getHealth());
        battleEffect.setPower(effect.getPower());
        battleEffect.setType(effect.getType());
        battleEffect.setTargetType(effect.getTargetType());
        battleEffect.getAbilities().addAll(effect.getAbilities());
    }
}
