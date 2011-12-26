package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Battle extends BaseEntity {

	BattleState getState();

	void setState(BattleState state);

	int getRound();

	void setRound(int round);

	Date getStartedAt();

	void setStartedAt(Date startedAt);

	List<BattleHero> getHeroes();

	List<BattleEffect> getEffects();

	boolean isReady();
}
