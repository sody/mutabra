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
    private Position position;

    private ObjectId hero;
    private Long creature;

    @Transient
    private BattleCreature creatureInstance;

    @Transient
    private BattleHero heroInstance;

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
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
