package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.*;
import com.mutabra.services.battle.BattleField;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CastScript extends AbstractScript {

    @Override
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final BattleField.Point target) {

        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();
        final BattleSpell battleSpell = battleEffect.getTarget().getSpell();
        if (casterUnit != null && battleSpell != null) {
            // subtract card blood cost from hero health
            casterUnit.setHealth(casterUnit.getHealth() - battleEffect.getPower());
            // move card to graveyard
            if (battleSpell.isCard()) {
                ((BattleCard) battleSpell).setType(BattleCardType.GRAVEYARD);
            }

            final BattleLogEntry logEntry = new BattleLogEntry();
            logEntry.setCode(battleEffect.getCode() + ".cast");
            final List<BattleLogParameter> parameters = logEntry.getParameters();
            parameters.add(new BattleLogParameter(casterUnit));
            parameters.add(new BattleLogParameter(battleSpell));
            parameters.add(new BattleLogParameter("-" + battleEffect.getPower()+ "/" + casterUnit.getHealth()));
            battleField.getBattle().getLog().add(logEntry);
        }
    }
}
