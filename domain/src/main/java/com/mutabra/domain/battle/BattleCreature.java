/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Effect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleCreature implements BattleUnit, Translatable {
    private Long id;
    private String code;
    private int health;
    private int power;
    private BattlePosition position;
    private boolean ready;

    private List<BattleAbility> abilities = new ArrayList<BattleAbility>();

    @Transient
    private BattleHero hero;

    protected BattleCreature() {
    }

    public BattleCreature(final BattleHero hero) {
        this.hero = hero;
        this.id = hero.getBattle().nextId();
    }

    public Long getId() {
        return id;
    }

    public BattleHero getHero() {
        return hero;
    }

    public String getBasename() {
        return Effect.BASENAME;
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

    public boolean isHero() {
        return false;
    }

    /* HELPER METHODS */
    void assignHero(final BattleHero hero) {
        this.hero = hero;
    }
}
