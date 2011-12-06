package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.common.Ability;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleAbility extends BaseEntity {

	BattleSummon getOwner();

	Ability getAbility();
}
