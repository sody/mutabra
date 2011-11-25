package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Card extends CodedEntity {

	CardType getType();

	Level getLevel();

	void setLevel(Level level);

	TargetType getTargetType();

	void setTargetType(TargetType targetType);

	int getBloodCost();

	void setBloodCost(int bloodCost);

	Effect getEffect();

	Summon getSummon();
}
