package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleCreature {

    private Long id;
    private String code;
    private int health;
    private int power;
    private Position position;
    private boolean ready;

    private List<BattleAbility> abilities = new ArrayList<BattleAbility>();

    // internal id sequence
    private long abilitySequence;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(final int power) {
        this.power = power;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(final boolean ready) {
        this.ready = ready;
    }

    public List<BattleAbility> getAbilities() {
        return abilities;
    }


    void assignId(final long id) {
        this.id = id;
    }

    long nextAbilityId() {
        return abilitySequence++;
    }
}
