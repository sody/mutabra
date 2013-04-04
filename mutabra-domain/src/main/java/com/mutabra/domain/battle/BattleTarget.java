package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Transient;
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
            hero = null;
            creature = null;
        } else if (unit.isHero()) {
            hero = ((BattleHero) unit).getId();
            creature = null;
        } else {
            hero = null;
            creature = ((BattleCreature) unit).getId();
        }
    }

    public BattleSpell getSpell() {
        return spell;
    }

    public void setSpell(final BattleSpell spell) {
        this.spell = spell;

        if (spell == null) {
            card = null;
            ability = null;
        } else if (spell.isCard()) {
            card = ((BattleCard) spell).getId();
            ability = null;
        } else {
            card = null;
            ability = ((BattleAbility) spell).getId();
        }
    }

    /* HELPER METHODS */
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
