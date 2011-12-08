package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.common.Effect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FakeScript implements EffectScript {

	public void execute(final BattleUnit caster, final Effect effect, final Object target) {
		//do nothing
	}
}
