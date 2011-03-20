package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleService {

	Player getCurrentPlayer();

	Battle getCurrentBattle();

	Battle createDuel(User user);
}
