package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
import com.mutabra.domain.battle.BattleUnit;
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

            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".success")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("target", new BattleLogParameter(battleCreature))
                    .append("target.health", new BattleLogParameter(battleCreature.getHealth()))
                    .append("target.power", new BattleLogParameter(battleCreature.getPower())));
        } else {
            battleField.getBattle().getLog().add(new BattleLogEntry(battleEffect.getCode() + ".failure")
                    .append("caster", new BattleLogParameter(casterUnit))
                    .append("spell", new BattleLogParameter(battleEffect)));
        }
    }
}
