package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
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
        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();

        if (target != null && target.hasUnit()) {
            final BattleUnit targetUnit = target.getUnit();
            targetUnit.setHealth(targetUnit.getHealth() - battleEffect.getPower());

            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".success")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("target", new BattleLogParameter(targetUnit))
                    .append("spell", new BattleLogParameter(battleEffect))
                    .append("spell.power", new BattleLogParameter(battleEffect.getPower()))
                    .append("target.health", new BattleLogParameter(targetUnit.getHealth())));
        } else {
            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".failure")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("spell", new BattleLogParameter(battleEffect)));
        }
    }
}
