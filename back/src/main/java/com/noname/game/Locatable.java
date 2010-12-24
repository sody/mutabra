package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Locatable {

	BattleField getField();

	Location getLocation();

	void setLocation(Location location);
}
