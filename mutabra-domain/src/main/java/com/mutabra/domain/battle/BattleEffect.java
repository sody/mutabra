package com.mutabra.domain.battle;

import com.google.code.morphia.annotations.Embedded;
import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class BattleEffect implements Translatable {

    private BattleTarget caster = new BattleTarget();
    private BattleTarget target = new BattleTarget();
    private String code;
    private EffectType type;
    private TargetType targetType;
    private int power;
    private int duration;
    private int health;

    private List<Ability> abilities = new ArrayList<Ability>();

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
}
