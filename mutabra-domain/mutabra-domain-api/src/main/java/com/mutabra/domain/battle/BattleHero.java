package com.mutabra.domain.battle;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.game.Hero;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleHero extends BattleUnit {

    Battle getBattle();

    Hero getHero();

    int getMentalPower();

    void setMentalPower(int mentalPower);

    List<Card> getDeck();

    List<Card> getHand();

    List<Card> getGraveyard();

    List<BattleCreature> getCreatures();
}
