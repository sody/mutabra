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

    private ObjectId heroId;
    private Long cardId;
    private Long creatureId;
    private Long abilityId;

    @Transient
    private BattleUnit unit;

    @Transient
    private BattleSpell spell;

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

    public BattleUnit getUnit() {
        return unit;
    }

    public void setUnit(final BattleUnit unit) {
        this.unit = unit;

        if (unit == null) {
            heroId = null;
            creatureId = null;
        } else if (unit.isHero()) {
            heroId = ((BattleHero) unit).getId();
            creatureId = null;
        } else {
            heroId = null;
            creatureId = ((BattleCreature) unit).getId();
        }
    }

    public BattleSpell getSpell() {
        return spell;
    }

    public void setSpell(final BattleSpell spell) {
        this.spell = spell;

        if (spell == null) {
            cardId = null;
            abilityId = null;
        } else if (spell.isCard()) {
            cardId = ((BattleCard) spell).getId();
            abilityId = null;
        } else {
            cardId = null;
            abilityId = ((BattleAbility) spell).getId();
        }
    }

    /* HELPER METHODS */
    ObjectId getHeroId() {
        return heroId;
    }

    Long getCardId() {
        return cardId;
    }

    Long getCreatureId() {
        return creatureId;
    }

    Long getAbilityId() {
        return abilityId;
    }
}
