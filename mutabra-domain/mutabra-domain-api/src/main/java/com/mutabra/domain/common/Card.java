package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Card extends CodedEntity {

	CardType getType();

	void setType(CardType type);

	Level getLevel();

	void setLevel(Level level);

	Effect getEffect();

	void setEffect(Effect effect);

	Summon getSummon();

	void setSummon(Summon summon);
}
