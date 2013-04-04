package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.*;
import com.mutabra.services.battle.BattleField;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CastScript extends AbstractScript {

    @Override
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final BattleField.Point target) {

        final BattleUnit battleUnit = battleEffect.getCaster().getUnit();
        final BattleSpell battleSpell = battleEffect.getTarget().getSpell();
        if (battleUnit != null && battleSpell != null) {
            // subtract card blood cost from hero health
            battleUnit.setHealth(battleUnit.getHealth() - battleEffect.getPower());
            // move card to graveyard
            if (battleSpell.isCard()) {
                ((BattleCard) battleSpell).setType(BattleCardType.GRAVEYARD);
            }
        }
    }
}
