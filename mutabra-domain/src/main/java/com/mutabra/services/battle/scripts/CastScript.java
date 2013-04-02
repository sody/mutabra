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

        final BattleCard battleCard = battleEffect.getTarget().getCard();
        final BattleHero battleHero = battleEffect.getCaster().getHero();
        if (battleCard != null && battleHero != null) {
            // subtract card blood cost from hero health
            battleHero.setHealth(battleHero.getHealth() - battleEffect.getPower());
            // move card to graveyard
            battleCard.setType(BattleCardType.GRAVEYARD);
        } else {
            final BattleAbility battleAbility = battleEffect.getTarget().getAbility();
            final BattleCreature battleCreature = battleEffect.getCaster().getCreature();

            if (battleAbility != null && battleCreature != null) {
                // subtract card blood cost from hero health
                battleCreature.setHealth(battleCreature.getHealth() - battleEffect.getPower());
            }
        }
    }
}
