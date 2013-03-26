package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattlePosition;
import com.mutabra.domain.battle.BattleSide;
import com.mutabra.domain.common.TargetType;
import com.mutabra.services.battle.BattleField;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractScript implements EffectScript {

    public void execute(final Battle battle, final BattleEffect battleEffect) {
        final BattleHero battleHero = battleEffect.getCaster().getHero() != null ?
                battleEffect.getCaster().getHero() :
                battleEffect.getCaster().getCreature().getHero(); // not null
        final BattleField battleField = BattleField.create(battle, battleHero);

        final TargetType targetType = battleEffect.getTargetType(); // not null
        if (targetType.isMassive()) {
            final List<BattleField.Point> targets = battleField.get(targetType);
            if (!targets.isEmpty()) {
                for (BattleField.Point target : targets) {
                    apply(battleField, battleEffect, target);
                }
            } else {
                // missed target
                apply(battleField, battleEffect, null);
            }
        } else {
            final BattlePosition position = battleEffect.getTarget().getPosition(); // can be null
            final BattleSide side = battleEffect.getTarget().getSide(); // can be null
            final BattleField.Point target = battleField.get(side, position);
            if (target.supports(targetType)) {
                apply(battleField, battleEffect, target);
            } else {
                // missed target
                apply(battleField, battleEffect, null);
            }
        }
    }

    protected abstract void apply(final BattleField battleField,
                                  final BattleEffect battleEffect,
                                  final BattleField.Point target);
}
