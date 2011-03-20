package com.mutabra.game;

import com.mutabra.domain.player.Hero;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleCommand {

	String getName();

	Battle getBattle();

	BattleField getField();

	Collection<BattlePlayer> getPlayers();

	BattlePlayer getPlayer(String name);

	void addPlayer(String name, Hero hero);
}
