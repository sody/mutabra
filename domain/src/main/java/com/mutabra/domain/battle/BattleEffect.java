/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.domain.battle;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.TargetType;
import org.mongodb.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleEffect implements Translatable, Comparable<BattleEffect> {

    private BattleTarget caster = new BattleTarget();
    private BattleTarget target = new BattleTarget();
    private String code;
    private EffectType type;
    private TargetType targetType;
    private int power;
    private int duration;
    private int health;

    private List<Ability> abilities = new ArrayList<Ability>();

    protected BattleEffect() {
    }

    public BattleEffect(final BattleSpell spell) {
        code = ((Translatable) spell).getCode();
        duration = 1;
        power = spell.getBloodCost();
        type = EffectType.CAST;
        targetType = TargetType.NOBODY;
        caster.setUnit(spell.getUnit());
        target.setSpell(spell);
    }

    public BattleEffect(final Effect effect) {
        code = effect.getCode();
        duration = effect.getDuration();
        health = effect.getHealth();
        power = effect.getPower();
        type = effect.getType();
        targetType = effect.getTargetType();
        abilities.addAll(effect.getAbilities());
    }

    public BattleTarget getCaster() {
        return caster;
    }

    public BattleTarget getTarget() {
        return target;
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

    public int compareTo(final BattleEffect o) {
        return type.compareTo(o.getType());
    }
}
