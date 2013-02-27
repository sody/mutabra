package com.mutabra.domain.battle;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleTarget {

    Position getPosition();

    void setPosition(Position position);

    BattleCreature getCreature();

    void setCreature(BattleCreature creature);

    BattleHero getHero();

    void setHero(BattleHero hero);
}
