package com.mutabra.domain.common;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Effect extends BaseEntity {

    Castable getCastable();

    String getScriptClass();

    void setScriptClass(String scriptClass);

    EffectType getEffectType();

    void setEffectType(EffectType effectType);

    TargetType getTargetType();

    void setTargetType(TargetType targetType);

    int getPower();

    void setPower(int power);

    int getDuration();

    void setDuration(int duration);

    int getHealth();

    void setHealth(int health);
}
