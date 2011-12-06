package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CastableImpl extends CodedEntityImpl implements Castable {

	private EffectType effectType;
	private TargetType targetType;
	private int bloodCost;
	private int strength;
	private int health;

	protected CastableImpl(final String type, final String code, final TranslationType translationType) {
		super(type, code, translationType);
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(final EffectType effectType) {
		this.effectType = effectType;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(final TargetType targetType) {
		this.targetType = targetType;
	}

	public int getBloodCost() {
		return bloodCost;
	}

	public void setBloodCost(final int bloodCost) {
		this.bloodCost = bloodCost;
	}

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
