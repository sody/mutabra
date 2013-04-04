package com.mutabra.domain.battle;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleUnit {

    int getHealth();

    void setHealth(int health);

    BattlePosition getPosition();

    void setPosition(BattlePosition position);

    boolean isReady();

    void setReady(boolean ready);

    boolean isHero();
}
