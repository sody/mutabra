package com.mutabra.domain.battle;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCard extends BattleAbility {

    BattleCardType getType();

    void setType(BattleCardType type);
}
