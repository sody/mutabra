package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.common.Effect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EffectScript {

	void execute(BattleUnit caster, Effect effect, Object target);
}
