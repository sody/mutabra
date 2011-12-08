package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Castable extends CodedEntity {

	String getScriptClass();

	EffectType getEffectType();

	void setEffectType(EffectType effectType);

	TargetType getTargetType();

	void setTargetType(TargetType targetType);

	int getBloodCost();

	void setBloodCost(int bloodCost);

	int getStrength();

	void setStrength(int strength);

	int getHealth();

	void setHealth(int health);
}
