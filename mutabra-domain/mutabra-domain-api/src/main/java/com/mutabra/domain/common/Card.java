package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Card extends CodedEntity {

	CardType getType();

	Effect getEffect();

	Summon getSummon();

	Level getLevel();

	void setLevel(Level level);
}
