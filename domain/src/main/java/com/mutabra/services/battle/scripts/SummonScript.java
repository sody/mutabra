/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.*;
import com.mutabra.domain.common.Ability;
import com.mutabra.services.battle.BattleField;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SummonScript extends AbstractScript {

    @Override
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final BattleField.Point target) {
        final BattleUnit casterUnit = battleEffect.getCaster().getUnit();// not null
        final BattleHero casterHero = casterUnit.isHero() ?
                (BattleHero) casterUnit :
                ((BattleCreature) casterUnit).getHero();

        if (target != null && !target.hasUnit()) {
            final BattleCreature battleCreature = new BattleCreature(casterHero);
            battleCreature.setCode(battleEffect.getCode());
            battleCreature.setHealth(battleEffect.getHealth());
            battleCreature.setPower(battleEffect.getPower());
            battleCreature.setPosition(target.getPosition());
            battleCreature.setReady(true);

            for (Ability ability : battleEffect.getAbilities()) {
                final BattleAbility battleAbility = new BattleAbility(battleCreature);
                battleAbility.setCode(ability.getCode());
                battleAbility.setTargetType(ability.getTargetType());
                battleAbility.setBloodCost(ability.getBloodCost());
                battleAbility.getEffects().addAll(ability.getEffects());
                battleCreature.getAbilities().add(battleAbility);
            }

            casterHero.getCreatures().add(battleCreature);

            success(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("target", battleCreature)
                    .parameter("target.health", battleCreature.getHealth())
                    .parameter("target.power", battleCreature.getPower())
                    .build(battleField.getBattle());
        } else {
            failure(battleEffect)
                    .parameter("caster", casterUnit)
                    .parameter("spell", battleEffect)
                    .build(battleField.getBattle());
        }
    }
}
