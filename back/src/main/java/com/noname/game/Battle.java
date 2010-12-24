package com.noname.game;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Battle {

	Collection<BattleCommand> getCommands();

	BattleCommand getCommand(String name);
}
