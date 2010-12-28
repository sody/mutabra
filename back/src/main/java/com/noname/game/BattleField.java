package com.noname.game;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleField {

	BattleCommand getCommand();

	List<Locatable> getLine(FieldLine line);

	Locatable getLocatable(Location location);

	void setLocatable(Location location, Locatable locatable);
}
