package com.noname.game;

import com.noname.domain.player.Hero;

import java.util.Collection;

/**
 * @author ivan.khalopik@tieto.com
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
