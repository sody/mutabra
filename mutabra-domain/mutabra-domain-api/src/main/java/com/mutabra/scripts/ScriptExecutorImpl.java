package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.common.Effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScriptExecutorImpl implements ScriptExecutor {
	private final Map<String, EffectScript> scripts = new HashMap<String, EffectScript>();

	public ScriptExecutorImpl(final List<EffectScript> scripts) throws Exception {
		for (EffectScript script : scripts) {
			this.scripts.put(script.getClass().getName(), script);
		}
	}

	public void executeScript(final BattleUnit caster, final Effect effect, final List<?> targets) {
		final EffectScript script = getScript(effect);
		for (Object target : targets) {
			script.execute(caster, effect, target);
		}
	}

	private EffectScript getScript(final Effect effect) {
		return scripts.get(effect.getScriptClass());
	}
}
