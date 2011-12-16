package com.mutabra.domain.battle;

import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Castable;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCreature extends BattleUnit {

	BattleHero getOwner();

	Card getCard();
}
