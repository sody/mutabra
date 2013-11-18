/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.common;

import org.mongodb.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class Effect implements Translatable {
    public static final String BASENAME = "effect";

    private String code;
    private EffectType type;
    private TargetType targetType;
    private int power;
    private int duration;
    private int health;

    private List<Ability> abilities = new ArrayList<Ability>();

    public String getBasename() {
        return BASENAME;
    }

    public String getCode() {
        return code;
    }

    public EffectType getType() {
        return type;
    }

    public void setType(final EffectType type) {
        this.type = type;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(final int power) {
        this.power = power;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }
}
