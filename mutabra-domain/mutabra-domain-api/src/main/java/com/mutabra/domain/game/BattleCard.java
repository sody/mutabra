package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCard extends BaseEntity {

	BattleMember getOwner();


	HeroCard getCard();

	boolean isInHand();

	void setInHand(boolean onHand);
}
