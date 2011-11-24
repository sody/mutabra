package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleSummon extends BaseEntity {

	BattleMember getOwner();


	HeroCard getCard();

	Position getPosition();

	int getHealth();

	void setHealth(int health);
}
