package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Transient;
import com.mutabra.domain.Translatable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleCreature implements Translatable {
    public static final String BASENAME = "creature";

    private Long id;
    private String code;
    private int health;
    private int power;
    private BattlePosition position;
    private boolean ready;

    private List<BattleAbility> abilities = new ArrayList<BattleAbility>();

    @Transient
    private BattleHero hero;

    public Long getId() {
        return id;
    }

    public BattleHero getHero() {
        return hero;
    }

    public String getBasename() {
        return BASENAME;
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

    public BattlePosition getPosition() {
        return position;
    }

    public void setPosition(final BattlePosition position) {
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

    /* HELPER METHODS */
    void assignHero(final BattleHero hero) {
        this.hero = hero;
    }

    void assignId(final long id) {
        this.id = id;
    }
}
