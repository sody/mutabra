package com.noname.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocatableImpl implements Locatable {
	private final BattleField field;

	private Location location;

	public LocatableImpl(final BattleField field) {
		this.field = field;
	}

	@Override
	public BattleField getField() {
		return field;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(final Location location) {
		field.setLocatable(location, this);
		this.location = location;
	}
}
