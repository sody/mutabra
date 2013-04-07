package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
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
            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".failure")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("spell", new BattleLogParameter(battleEffect)));
        } else if (target.hasUnit()) {
            final BattleUnit targetUnit = target.getUnit();

            // change their positions
            final BattlePosition position = casterUnit.getPosition();
            casterUnit.setPosition(targetUnit.getPosition());
            targetUnit.setPosition(position);

            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".success")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("target", new BattleLogParameter(targetUnit))
                    .append("position", new BattleLogParameter(BattleSide.YOUR, target.getPosition()))
                    .append("spell", new BattleLogParameter(battleEffect)));
        } else {
            // just move caster to new position
            casterUnit.setPosition(target.getPosition());

            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".success")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("position", new BattleLogParameter(BattleSide.YOUR, target.getPosition()))
                    .append("spell", new BattleLogParameter(battleEffect)));
        }
    }
}
