package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.game.Hero;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleMember extends BaseEntity {

	Battle getBattle();


	Hero getHero();

	Set<BattleCard> getCards();

	Set<BattleCard> getDeck();

	Set<BattleCard> getHand();

	Set<BattleSummon> getSummons();

	Position getPosition();

	int getHealth();

	void setHealth(int health);

}
