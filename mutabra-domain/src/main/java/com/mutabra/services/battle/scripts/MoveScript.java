package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattlePosition;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.services.battle.BattleField;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MoveScript extends AbstractScript {
    @Override
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final BattleField.Point target) {

        if (target == null || target.isEnemySide()) {
            //TODO: log miss
        } else if (target.hasUnit()) {
            final BattleUnit casterUnit = battleEffect.getCaster().getUnit();
            final BattleUnit targetUnit = target.getUnit();

            // change their positions
            final BattlePosition position = casterUnit.getPosition();
            casterUnit.setPosition(targetUnit.getPosition());
            targetUnit.setPosition(position);
        } else {
            // just move caster to new position
            battleEffect.getCaster().getUnit().setPosition(target.getPosition());
        }
    }
}
