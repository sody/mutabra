package com.mutabra.domain.common;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Effect {

	TargetType getTargetType();

	void setTargetType(TargetType targetType);

	int getAttack();

	void setAttack(int attack);

	int getDefence();

	void setDefence(int defence);
}
