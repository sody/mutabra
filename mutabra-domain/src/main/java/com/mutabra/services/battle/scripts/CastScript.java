package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCardType;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
import com.mutabra.domain.battle.BattleSpell;
import com.mutabra.domain.battle.BattleUnit;
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

        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();
        final BattleSpell battleSpell = battleEffect.getTarget().getSpell();
        if (casterUnit != null && battleSpell != null) {
            // subtract card blood cost from hero health
            casterUnit.setHealth(casterUnit.getHealth() - battleEffect.getPower());
            // move card to graveyard
            if (battleSpell.isCard()) {
                ((BattleCard) battleSpell).setType(BattleCardType.GRAVEYARD);
            }

//            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".cast")
//                    .append("caster", new BattleLogParameter(casterUnit))
//                    .append("spell", new BattleLogParameter(battleSpell))
//                    .append("spell.cost", new BattleLogParameter(battleSpell.getBloodCost()))
//                    .append("caster.health", new BattleLogParameter(casterUnit.getHealth())));
        }
    }
}
