package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattlePosition;
import com.mutabra.domain.battle.BattleSide;
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
        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();

        if (target == null || target.isEnemySide()) {
            failure(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("spell", battleEffect)
                    .build(battleField.getBattle());
        } else if (target.hasUnit()) {
            final BattleUnit targetUnit = target.getUnit();

            // change their positions
            final BattlePosition position = casterUnit.getPosition();
            casterUnit.setPosition(targetUnit.getPosition());
            targetUnit.setPosition(position);

            success(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("target", targetUnit)
                    .parameter("position", BattleSide.YOUR, target.getPosition())
                    .parameter("spell", battleEffect)
                    .build(battleField.getBattle());
        } else {
            // just move caster to new position
            casterUnit.setPosition(target.getPosition());

            success(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("position", BattleSide.YOUR, target.getPosition())
                    .parameter("spell", battleEffect)
                    .build(battleField.getBattle());
        }
    }
}
