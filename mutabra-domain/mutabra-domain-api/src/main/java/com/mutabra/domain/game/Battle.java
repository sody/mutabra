package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Battle extends BaseEntity {

	Set<BattleMember> getMembers();

	int getRound();

	void setRound(int round);
}
