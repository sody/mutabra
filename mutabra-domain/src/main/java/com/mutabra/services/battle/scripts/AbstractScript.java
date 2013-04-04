package com.mutabra.services.battle.scripts;

import com.mutabra.domain.battle.*;
import com.mutabra.domain.common.TargetType;
import com.mutabra.services.battle.BattleField;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractScript implements EffectScript {
    private static final List<BattleField.Point> EMPTY_TARGETS = Collections.emptyList();

    public void execute(final Battle battle, final BattleEffect battleEffect) {
        // get caster hero
        final BattleUnit battleUnit = battleEffect.getCaster().getUnit(); // not null
        final BattleHero battleHero = battleUnit.isHero() ?
                (BattleHero) battleUnit :
                ((BattleCreature) battleUnit).getHero();
        // create battle field
        final BattleField battleField = BattleField.create(battle, battleHero);

        final TargetType targetType = battleEffect.getTargetType(); // not null
        if (targetType.isMassive()) {
            final List<BattleField.Point> targets = battleField.get(targetType);

            apply(battleField, battleEffect, targets);
        } else {
            final BattlePosition position = battleEffect.getTarget().getPosition(); // can be null
            final BattleSide side = battleEffect.getTarget().getSide(); // can be null
            final BattleField.Point target = position != null && side != null ?
                    battleField.get(side, position) : null;

            apply(battleField, battleEffect, target != null && target.supports(targetType) ?
                    Collections.singletonList(target) : EMPTY_TARGETS);
        }
    }

    /**
     * Applies battle effect on multiple targeted point.
     *
     * @param battleField  battle field view for caster, not {@code null}
     * @param battleEffect battle effect to apply, not {@code null}
     * @param targets      multiple targeted point, not {@code null}, can be empty if targets are not found
     */
    protected void apply(final BattleField battleField,
                         final BattleEffect battleEffect,
                         final List<BattleField.Point> targets) {
        if (targets.isEmpty()) {
            // missed target
            apply(battleField, battleEffect, (BattleField.Point) null);
        } else {
            // apply for each target
            for (BattleField.Point target : targets) {
                apply(battleField, battleEffect, target);
            }
        }
    }

    /**
     * Applies battle effect on single targeted point.
     *
     * @param battleField  battle field view for caster, not {@code null}
     * @param battleEffect battle effect to apply, not {@code null}
     * @param target       single targeted point, can be {@code null} if target is not found
     */
    protected abstract void apply(final BattleField battleField,
                                  final BattleEffect battleEffect,
                                  final BattleField.Point target);
}
