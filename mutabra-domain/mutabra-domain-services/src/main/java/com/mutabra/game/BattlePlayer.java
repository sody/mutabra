package com.mutabra.game;

import com.mutabra.domain.player.Hero;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattlePlayer extends Locatable {

	BattleCommand getCommand();

	String getName();

	Hero getHero();

	int getHealth();

	void setHealth(int health);
}
