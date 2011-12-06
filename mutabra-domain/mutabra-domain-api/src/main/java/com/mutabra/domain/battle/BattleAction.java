package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleAction extends BaseEntity {

	Battle getBattle();

	int getRound();

	BattleCard getCard();

	void setCard(BattleCard card);

	BattleAbility getAbility();

	void setAbility(BattleAbility ability);

	Position getTarget();

	void setTarget(Position target);
}
