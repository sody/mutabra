/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleTarget {
    private BattlePosition position;
    private BattleSide side;

    private ObjectId hero;
    private Long card;
    private Long creature;
    private Long ability;

    @Transient
    private BattleCreature creatureInstance;

    @Transient
    private BattleHero heroInstance;

    @Transient
    private BattleCard cardInstance;

    @Transient
    private BattleAbility abilityInstance;

    public BattlePosition getPosition() {
        return position;
    }

    public void setPosition(final BattlePosition position) {
        this.position = position;
    }

    public BattleSide getSide() {
        return side;
    }

    public void setSide(final BattleSide side) {
        this.side = side;
    }

    public BattleHero getHero() {
        return heroInstance;
    }

    public void setHero(final BattleHero hero) {
        this.heroInstance = hero;
        this.hero = hero.getId();
    }

    public BattleCard getCard() {
        return cardInstance;
    }

    public void setCard(final BattleCard card) {
        this.cardInstance = card;
        this.card = card.getId();
    }

    public BattleCreature getCreature() {
        return creatureInstance;
    }

    public void setCreature(final BattleCreature creature) {
        this.creatureInstance = creature;
        this.creature = creature.getId();
    }

    public BattleAbility getAbility() {
        return abilityInstance;
    }

    public void setAbility(final BattleAbility ability) {
        this.abilityInstance = ability;
        this.ability = ability.getId();
    }

    ObjectId getHeroId() {
        return hero;
    }

    Long getCardId() {
        return card;
    }

    Long getCreatureId() {
        return creature;
    }

    Long getAbilityId() {
        return ability;
    }
}
