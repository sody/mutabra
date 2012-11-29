package com.mutabra.scripts;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScriptContextImpl implements ScriptContext {
    private final Battle battle;
    private final BattleUnit caster;
    private final Effect effect;
    private final Position target;

    private final Map<Position, BattleField> battleField = new HashMap<Position, BattleField>();

    public ScriptContextImpl(final Battle battle, final BattleEffect effect) {
        this.battle = battle;
        this.caster = effect.getCaster();
        this.effect = effect.getEffect();
        this.target = effect.getTarget();

        boolean upSide = false;
        for (BattleHero hero : battle.getHeroes()) {
            final boolean enemy = !hero.equals(caster);
            if (!enemy && hero.getPosition().getY() == 0) {
                upSide = true;
            }
            battleField.put(hero.getPosition(), new BattleField(hero, enemy));
            for (BattleCreature creature : hero.getCreatures()) {
                battleField.put(creature.getPosition(), new BattleField(creature, enemy));
            }
        }

        for (Position position : BattleField.DUEL_POSITIONS) {
            if (!battleField.containsKey(position)) {
                final boolean enemy = (position.getY() == 0 && !upSide) || (position.getY() > 0 && upSide);
                battleField.put(position, new BattleField(position, enemy));
            }
        }
    }

    public Battle getBattle() {
        return battle;
    }

    public BattleUnit getCaster() {
        return caster;
    }

    public Effect getEffect() {
        return effect;
    }

    public Position getTarget() {
        return target;
    }

    public List<BattleField> getTargets() {
        final ArrayList<BattleField> targets = new ArrayList<BattleField>();
        final TargetType targetType = effect.getTargetType();
        if (targetType.isMassive()) {
            for (BattleField field : battleField.values()) {
                if (field.supports(targetType)) {
                    targets.add(field);
                }
            }
        } else {
            for (BattleField field : battleField.values()) {
                if (field.supports(targetType) && field.getPosition().equals(target)) {
                    targets.add(field);
                    break;
                }
            }
        }
        return targets;
    }
}
