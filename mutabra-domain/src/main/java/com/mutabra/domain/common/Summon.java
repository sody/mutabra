package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Summon extends CodedEntity {

	int getAttack();

	void setAttack(int attack);

	int getDefence();

	void setDefence(int defence);
}
