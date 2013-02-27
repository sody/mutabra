package com.mutabra.domain.battle;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCreature {

    Long getId();

    String getCode();

    void setCode(String code);

    int getHealth();

    void setHealth(int health);

    int getPower();

    void setPower(int mentalPower);

    Position getPosition();

    void setPosition(Position position);

    boolean isReady();

    void setReady(boolean ready);

    List<BattleAbility> getAbilities();
}
