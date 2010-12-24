package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService {

	void createDuel(String name, String rivalName);

	BattlePlayer getPlayer(String name);
}
