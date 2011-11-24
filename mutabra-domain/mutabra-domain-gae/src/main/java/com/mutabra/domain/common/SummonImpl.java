package com.mutabra.domain.common;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SummonImpl implements Summon {

	private int strength;

	private int health;

	public int getStrength() {
		return strength;
	}

	public void setStrength(final int strength) {
		this.strength = strength;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}
}
