package com.mutabra.domain.battle;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.common.Effect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BattleEffect extends BaseEntity {

	Battle getBattle();

	Effect getEffect();

	BattleUnit getCaster();

	void setCaster(BattleUnit caster);

	Position getTarget();

	void setTarget(Position target);

	int getDuration();

	void setDuration(int duration);
}
