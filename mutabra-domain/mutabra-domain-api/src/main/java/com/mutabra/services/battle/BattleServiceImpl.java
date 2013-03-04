package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCardType;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.mutabra.services.Mappers.card$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleServiceImpl extends BaseEntityServiceImpl<Battle> implements BattleService {
    private final ScriptEngine scriptEngine;

    public BattleServiceImpl(final Repository repository,
                             final ScriptEngine scriptEngine) {
        super(repository, Battle.class);

        this.scriptEngine = scriptEngine;
    }

    public void create(final Hero hero1, final Hero hero2) {
        final Battle battle = create();
        battle.setActive(false);

        // init heroes
        for (Hero hero : Arrays.asList(hero1, hero2)) {
            final BattleHero battleHero = battle.createHero();
            fillHero(battleHero, hero);

            battleHero.setReady(false);
            battle.getHeroes().add(battleHero);
        }
        // set first hero ready for battle
        battle.getHeroes().get(0).setReady(true);

        save(battle);
    }

    public void start(final Battle battle) {
        battle.setActive(true);
        battle.setStartedAt(new Date());
        battle.setRound(1);

        for (BattleHero battleHero : battle.getHeroes()) {
            final Hero hero = battleHero.getHero();
            fillHero(battleHero, hero);

            battleHero.setPosition(new Position(1, 0));
            battleHero.setReady(false);

            // init hero cards
            final List<Card> cards = repository().query(Card.class)
                    .filter(card$.code$.in(hero.getCards()))
                    .list();
            Collections.shuffle(cards);

            for (Card card : cards) {
                final BattleCard battleCard = battle.createCard();
                fillCard(battleCard, card);

                battleCard.setType(BattleCardType.DECK);
                battleHero.getCards().add(battleCard);
            }

            // get 3 first cards from deck to hand
            for (int i = 0; i < 3; i++) {
                battleHero.getCards().get(i).setType(BattleCardType.HAND);
            }
        }

        update(battle);
    }

    public void end(final Battle battle) {
        for (BattleHero battleHero : battle.getHeroes()) {
            final Hero hero = battleHero.getHero();
            hero.getLevel().setRating(battleHero.getLevel().getRating());
            repository().update(hero);
        }
        delete(battle);
    }

    public void endRound(final Battle battle) {
        // process effects
        scriptEngine.executeScripts(battle);

        for (BattleHero battleHero : battle.getHeroes()) {
            if (battleHero.getHealth() <= 0 || battleHero.getMentalPower() <= 0) {
                end(battle);
                return;
            }
        }

        //start new round
        battle.setRound(battle.getRound() + 1);
        for (BattleHero battleHero : battle.getHeroes()) {
            battleHero.setMentalPower(battleHero.getMentalPower() + 1);

            final List<BattleCard> deck = battleHero.getDeck();
            if (deck.size() > 0) {
                deck.get(0).setType(BattleCardType.HAND);
            }
            battleHero.setReady(battleHero.getHand().isEmpty());

            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                battleCreature.setReady(battleCreature.getAbilities().isEmpty());
            }
        }
        update(battle);
    }

    public void cast(final Battle battle,
                     final BattleHero hero,
                     final BattleCard card,
                     final Position target) {
        for (Effect effect : card.getEffects()) {
            final BattleEffect battleEffect = battle.createEffect();
            fillEffect(battleEffect, effect);

            battleEffect.setCode(card.getCode());
            battleEffect.getTarget().setPosition(target);
            battleEffect.getCaster().setHero(hero);

            battle.getEffects().add(battleEffect);
        }

        hero.setReady(true);
        //TODO: replace with some battle effect that takes place after round ends
        hero.setHealth(hero.getHealth() - card.getBloodCost());
        card.setType(BattleCardType.GRAVEYARD);

        if (battle.isAllReady()) {
            endRound(battle);
        } else {
            update(battle);
        }
    }

    public void cast(final Battle battle,
                     final BattleCreature creature,
                     final BattleAbility ability,
                     final Position target) {
        for (Effect effect : ability.getEffects()) {
            final BattleEffect battleEffect = battle.createEffect();
            fillEffect(battleEffect, effect);

            battleEffect.setCode(ability.getCode());
            battleEffect.getTarget().setPosition(target);
            battleEffect.getCaster().setCreature(creature);

            battle.getEffects().add(battleEffect);
        }

        creature.setReady(true);
        //TODO: replace with some battle effect that takes place after round ends
        creature.setHealth(creature.getHealth() - ability.getBloodCost());

        if (battle.isAllReady()) {
            endRound(battle);
        } else {
            update(battle);
        }
    }

    public void skip(final Battle battle, final BattleHero hero) {
        hero.setReady(true);

        for (BattleCreature battleCreature : hero.getCreatures()) {
            battleCreature.setReady(true);
        }

        if (battle.isAllReady()) {
            endRound(battle);
        } else {
            update(battle);
        }
    }

    private void fillHero(final BattleHero battleHero, final Hero hero) {
        battleHero.setHero(hero);

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
        battleEffect.setDuration(effect.getDuration());
        battleEffect.setHealth(effect.getHealth());
        battleEffect.setPower(effect.getPower());
        battleEffect.setType(effect.getType());
        battleEffect.setTargetType(effect.getTargetType());
        battleEffect.getAbilities().addAll(effect.getAbilities());
    }
}
