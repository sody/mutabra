package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CastableImpl extends CodedEntityImpl implements Castable {

    private TargetType targetType;
    private int bloodCost;
    private int power;
    private int duration;
    private int health;

    protected CastableImpl(final String type, final String code, final TranslationType translationType) {
        super(type, code, translationType);
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    public int getBloodCost() {
        return bloodCost;
    }

    public void setBloodCost(final int bloodCost) {
        this.bloodCost = bloodCost;
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

    public List<Effect> getEffects() {
        return Keys.getChildren(Effect.class, EffectImpl.class, this);
    }
}
