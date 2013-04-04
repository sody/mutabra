package com.mutabra.domain.battle;

import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleSpell {

    BattleUnit getUnit();

    TargetType getTargetType();

    void setTargetType(TargetType targetType);

    int getBloodCost();

    void setBloodCost(int bloodCost);

    List<Effect> getEffects();

    boolean isCard();
}
