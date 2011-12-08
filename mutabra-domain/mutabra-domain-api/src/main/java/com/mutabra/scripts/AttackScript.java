package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleMember;
import com.mutabra.domain.common.Castable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript implements EffectScript {

	public void execute(final Castable castable, final Object target) {
		if (target != null && target instanceof BattleMember) {
			final int strength = castable.getStrength();
			final BattleMember member = (BattleMember) target;
			member.setHealth(member.getHealth() - strength);
		}
	}

}
