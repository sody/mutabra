package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.game.HeroCard;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleSummon extends BaseEntity {

	BattleMember getOwner();

	HeroCard getCard();

	Position getPosition();

	void setPosition(Position position);

	int getHealth();

	void setHealth(int health);

	boolean isExhausted();

	void setExhausted(boolean exhausted);
}
