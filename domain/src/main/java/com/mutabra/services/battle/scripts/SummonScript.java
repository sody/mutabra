/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
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
        if (target == null || target.hasUnit()) {
            //TODO: log fail
        } else if (!target.hasUnit()) {
            final BattleCreature battleCreature = new BattleCreature();
            battleCreature.setCode(battleEffect.getCode());
            battleCreature.setHealth(battleEffect.getHealth());
            battleCreature.setPower(battleEffect.getPower());
            battleCreature.setPosition(target.getPosition());
            battleCreature.setReady(true);

            for (Ability ability : battleEffect.getAbilities()) {
                final BattleAbility battleAbility = new BattleAbility();
                battleAbility.setCode(ability.getCode());
                battleAbility.setTargetType(ability.getTargetType());
                battleAbility.setBloodCost(ability.getBloodCost());
                battleAbility.getEffects().addAll(ability.getEffects());
                battleCreature.getAbilities().add(battleAbility);
            }

            final BattleHero battleHero = battleEffect.getCaster().getHero(); // not null
            battleHero.getCreatures().add(battleCreature);
        }
    }
}
