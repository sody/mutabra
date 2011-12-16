package com.mutabra.domain.battle;

import com.mutabra.domain.common.Effect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCreature extends BattleUnit {

	BattleHero getOwner();

	Effect getEffect();
}
