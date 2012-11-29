package com.mutabra.domain.battle;

import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleUnitImpl extends BaseEntityImpl implements BattleUnit {

    @Unindexed
    @Embedded
    private Position position;

    @Unindexed
    private int health;

    @Unindexed
    private boolean exhausted;

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhausted(final boolean exhausted) {
        this.exhausted = exhausted;
    }
}
