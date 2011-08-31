package com.mutabra.game;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocatableImpl implements Locatable {
	private final BattleField field;
	private final String name;

	private Location location;

	public LocatableImpl(final BattleField field, final String name) {
		this.field = field;
		this.name = name;
	}

	public BattleField getField() {
		return field;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		field.setLocatable(location, this);
		this.location = location;
	}
}
