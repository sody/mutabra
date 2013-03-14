package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.TargetType;
import com.mutabra.services.battle.BattleField;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AttackScript implements EffectScript {

    public void execute(final Battle battle,
                        final BattleEffect battleEffect) {

        final BattleHero battleHero = battleEffect.getCaster().getHero(); // not null
        //TODO: add more complex logic of target retrieval
        final Position position = battleEffect.getTarget().getPosition(); // can be null
        final TargetType targetType = battleEffect.getTargetType(); // not null

        final BattleField battleField = BattleField.create(battle, battleHero);
        final List<BattleField.Point> targets = battleField.get(position, targetType);

        //TODO: log miss if targets is empty
        for (BattleField.Point target : targets) {
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
}
