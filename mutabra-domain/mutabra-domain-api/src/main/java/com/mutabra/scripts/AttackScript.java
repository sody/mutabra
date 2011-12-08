package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.common.Effect;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript implements EffectScript {

	public void execute(final BattleUnit caster, final Effect effect, final Object target) {
		if (target != null) {
			final int power = effect.getPower();
			if (target instanceof BattleUnit) {
				final BattleUnit unit = (BattleUnit) target;
				unit.setHealth(unit.getHealth() - power);
			}
		}
	}
}
