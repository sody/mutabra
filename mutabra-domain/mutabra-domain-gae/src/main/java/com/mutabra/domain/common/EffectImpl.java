package com.mutabra.domain.common;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EffectImpl implements Effect {

	private int strength;
	private int duration;

	public int getStrength() {
		return strength;
	}

	public void setStrength(final int strength) {
		this.strength = strength;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}
}
