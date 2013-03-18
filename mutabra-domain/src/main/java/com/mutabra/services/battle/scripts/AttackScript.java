package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.services.battle.BattleField;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript extends AbstractScript {

    @Override
    protected void apply(final BattleEffect battleEffect,
                         final BattleField battleField,
                         final BattleField.Point target) {
        if (target.hasHero()) {
            final BattleHero targetHero = target.getHero();
            targetHero.setHealth(targetHero.getHealth() - battleEffect.getPower());
        } else if (target.hasCreature()) {
            final BattleCreature targetCreature = target.getCreature();
            targetCreature.setHealth(targetCreature.getHealth() - battleEffect.getPower());
        } else {
            //TODO: log miss
        }
    }
}
