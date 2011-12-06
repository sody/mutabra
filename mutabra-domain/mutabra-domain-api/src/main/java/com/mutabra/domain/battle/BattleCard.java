package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.game.HeroCard;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCard extends BaseEntity {

	BattleMember getOwner();

	HeroCard getCard();

	BattleCardState getState();

	void setState(BattleCardState state);
}
