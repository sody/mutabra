package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.services.battle.BattleField;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript extends AbstractScript {

    @Override
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final BattleField.Point target) {
        if (target != null && target.hasUnit()) {
            final BattleUnit battleUnit = target.getUnit();
            battleUnit.setHealth(battleUnit.getHealth() - battleEffect.getPower());
        } else {
            //TODO: log miss
        }
    }
}
