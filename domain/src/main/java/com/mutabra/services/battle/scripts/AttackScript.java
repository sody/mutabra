/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

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
        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();

        if (target != null && target.hasUnit()) {
            final BattleUnit targetUnit = target.getUnit();
            targetUnit.setHealth(targetUnit.getHealth() - battleEffect.getPower());

            success(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("target", targetUnit)
                    .parameter("spell", battleEffect)
                    .parameter("spell.power", battleEffect.getPower())
                    .parameter("target.health", targetUnit.getHealth())
                    .build(battleField.getBattle());
        } else {
            failure(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("spell", battleEffect)
                    .build(battleField.getBattle());
        }
    }
}
