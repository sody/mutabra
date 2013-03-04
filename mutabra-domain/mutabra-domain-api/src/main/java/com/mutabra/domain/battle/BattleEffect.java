package com.mutabra.domain.battle;

import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.EffectType;
import com.mutabra.domain.common.TargetType;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleEffect {

    BattleTarget getCaster();

    BattleTarget getTarget();

    String getCode();

    void setCode(String code);

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
