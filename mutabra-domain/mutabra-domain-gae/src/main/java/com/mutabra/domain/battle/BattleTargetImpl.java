package com.mutabra.domain.battle;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.NotSaved;
import com.mutabra.domain.game.HeroImpl;

import javax.persistence.Embeddable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embeddable
public class BattleTargetImpl implements BattleTarget {
    private Position position;

    private Key<HeroImpl> heroId;
    private Long creatureId;

    @NotSaved
    private BattleHero hero;

    @NotSaved
    private BattleCreature creature;

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public BattleCreature getCreature() {
        return creature;
    }

    public void setCreature(final BattleCreature creature) {
        this.creature = creature;

        creatureId = creature.getId();
    }

    public BattleHero getHero() {
        return hero;
    }

    public void setHero(final BattleHero hero) {
        this.hero = hero;

        heroId = ((BattleHeroImpl) hero).getHeroKey();
    }

    public Key<HeroImpl> getHeroKey() {
        return heroId;
    }

    public Long getCreatureId() {
        return creatureId;
    }
}
