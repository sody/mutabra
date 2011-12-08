package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Castable extends CodedEntity {

	TargetType getTargetType();

	void setTargetType(TargetType targetType);

	int getBloodCost();

	void setBloodCost(int bloodCost);

	int getPower();

	void setPower(int power);

	int getDuration();

	void setDuration(int duration);

	int getHealth();

	void setHealth(int health);

	List<Effect> getEffects();
}
