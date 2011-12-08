package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleUnit extends BaseEntity {

	Position getPosition();

	void setPosition(Position position);

	int getHealth();

	void setHealth(int health);

	boolean isExhausted();

	void setExhausted(boolean exhausted);
}
