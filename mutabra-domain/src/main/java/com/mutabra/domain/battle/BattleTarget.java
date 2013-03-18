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
    private Long creature;

    @Transient
    private BattleCreature creatureInstance;

    @Transient
    private BattleHero heroInstance;

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

    public BattleCreature getCreature() {
        return creatureInstance;
    }

    public void setCreature(final BattleCreature creature) {
        this.creatureInstance = creature;
        this.creature = creature.getId();
    }


    public ObjectId getHeroId() {
        return hero;
    }

    public Long getCreatureId() {
        return creature;
    }
}
