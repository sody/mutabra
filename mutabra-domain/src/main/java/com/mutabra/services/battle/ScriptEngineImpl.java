package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleLogEntry;
import com.mutabra.domain.battle.BattleLogParameter;
import com.mutabra.domain.common.EffectType;
import com.mutabra.services.battle.scripts.EffectScript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScriptEngineImpl implements ScriptEngine {
    private final Map<EffectType, EffectScript> scripts;

    public ScriptEngineImpl(final Map<EffectType, EffectScript> scripts) {
        this.scripts = scripts;
    }

    public void executeScripts(final Battle battle) {
        // add round started message to log
        battle.getLog().add(new BattleLogEntry("round.start")
                .append("round", new BattleLogParameter(battle.getRound())));

        // process effects
        final List<BattleEffect> deadEffects = new ArrayList<BattleEffect>();
        // sort effects
        Collections.sort(battle.getEffects());

        for (BattleEffect battleEffect : battle.getEffects()) {
            final EffectScript script = scripts.get(battleEffect.getType());
            if (script != null) {
                script.execute(battle, battleEffect);
            }

            battleEffect.setDuration(battleEffect.getDuration() - 1);
            if (battleEffect.getDuration() <= 0) {
                deadEffects.add(battleEffect);
            }
        }
        // remove dead effects
        battle.getEffects().removeAll(deadEffects);

        // remove dead creatures
        for (BattleHero battleHero : battle.getHeroes()) {
            // someone dead
            if (battleHero.getHealth() <= 0 || battleHero.getMentalPower() <= 0) {
                //TODO: log dead hero
                battle.setActive(false);
            }

            final List<BattleCreature> deadCreatures = new ArrayList<BattleCreature>();
            for (BattleCreature creature : battleHero.getCreatures()) {
                // creature is dead
                if (creature.getHealth() <= 0) {
                    deadCreatures.add(creature);
                }
            }
            //TODO: log dead creatures
            battleHero.getCreatures().removeAll(deadCreatures);
        }

        // add round ended message to log
        battle.getLog().add(new BattleLogEntry("round.end")
                .append("round", new BattleLogParameter(battle.getRound())));
    }
}
