package com.mutabra.domain.game;

import com.mutabra.domain.BaseEntity;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleMember extends BaseEntity {

	Battle getBattle();


	Hero getHero();

	Set<BattleCard> getCards();

	Set<BattleSummon> getSummons();

	int getPosition();

	void setPosition(int position);

	int getHealth();

	void setHealth(int health);

}
