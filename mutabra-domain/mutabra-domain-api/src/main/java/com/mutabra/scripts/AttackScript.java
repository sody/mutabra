package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleUnit;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript implements EffectScript {

    public void execute(final ScriptContext context) {
        final int power = context.getEffect().getPower();
        for (BattleField field : context.getTargets()) {
            if (field.hasUnit()) {
                final BattleUnit unit = field.getUnit();
                unit.setHealth(unit.getHealth() - power);
            }
        }
    }
}
