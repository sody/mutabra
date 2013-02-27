package com.mutabra.domain.common;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Effect {

    EffectType getType();

    void setType(EffectType type);

    TargetType getTargetType();

    void setTargetType(TargetType targetType);

    int getPower();

    void setPower(int power);

    int getDuration();

    void setDuration(int duration);

    int getHealth();

    void setHealth(int health);

    List<Ability> getAbilities();
}
