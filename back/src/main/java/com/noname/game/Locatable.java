package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Locatable {

	BattleField getField();

	String getName();

	Location getLocation();

	void setLocation(Location location);
}
