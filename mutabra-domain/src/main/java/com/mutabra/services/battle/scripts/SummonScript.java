package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Ability;
import com.mutabra.domain.common.TargetType;
import com.mutabra.services.battle.BattleField;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SummonScript implements EffectScript {

    public void execute(final Battle battle,
                        final BattleEffect battleEffect) {

        final BattleHero battleHero = battleEffect.getCaster().getHero(); // not null
        //TODO: add more complex logic of target retrieval
        final Position position = battleEffect.getTarget().getPosition(); // can be null
        final TargetType targetType = battleEffect.getTargetType(); // not null

        final BattleField battleField = BattleField.create(battle, battleHero);
        final List<BattleField.Point> targets = battleField.get(position, targetType);

        //TODO: log fail if targets is empty
        for (BattleField.Point target : targets) {
            if (!target.hasUnit()) {
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

                battleHero.getCreatures().add(battleCreature);
            } else {
                //TODO: log fail
            }
        }
    }
}
