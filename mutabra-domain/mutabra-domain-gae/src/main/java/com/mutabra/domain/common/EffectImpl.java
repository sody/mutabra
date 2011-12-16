package com.mutabra.domain.common;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;
import com.mutabra.domain.Keys;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.EFFECT)
public class EffectImpl extends BaseEntityImpl implements Effect {

	@Parent
	private Key<CastableImpl> castable;

	private String scriptClass;
	private EffectType effectType;
	private TargetType targetType;
	private int power;
	private int duration;
	private int health;

	public EffectImpl() {
	}

	public EffectImpl(final Castable castable) {
		this.castable = Keys.getKey(castable);
	}

	public Castable getCastable() {
		return Keys.getInstance(castable);
	}

	public String getScriptClass() {
		return scriptClass;
	}

	public void setScriptClass(final String scriptClass) {
		this.scriptClass = scriptClass;
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

	public int getPower() {
		return power;
	}

	public void setPower(final int power) {
		this.power = power;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(final int health) {
		this.health = health;
	}

	@Override
	public Key<?> getParentKey() {
		return castable;
	}
}
