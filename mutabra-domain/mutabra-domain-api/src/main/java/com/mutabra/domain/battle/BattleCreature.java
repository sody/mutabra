package com.mutabra.domain.battle;

import com.mutabra.domain.common.Card;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCreature extends BattleUnit {

    BattleHero getOwner();

    Card getCard();
}
