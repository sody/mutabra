package com.mutabra.domain.common;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Effect {

	TargetType getTargetType();

	void setTargetType(TargetType targetType);

	int getStrength();

	void setStrength(int strength);

	int getDuration();

	void setDuration(int duration);
}
