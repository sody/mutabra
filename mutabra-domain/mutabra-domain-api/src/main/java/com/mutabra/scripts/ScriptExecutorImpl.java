package com.mutabra.scripts;

import com.mutabra.domain.common.Castable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScriptExecutorImpl implements ScriptExecutor {
	private final Map<String, EffectScript> scripts = new HashMap<String, EffectScript>();

	public ScriptExecutorImpl(Collection<Class> scriptClasses) throws Exception {
		for (Class scriptClass : scriptClasses) {
			scripts.put(scriptClass.getName(), (EffectScript) scriptClass.newInstance());
		}
	}

	public void executeScript(final Castable castable, final List<?> targets) {
		final EffectScript script = getScript(castable);
		for (Object target : targets) {
			script.execute(castable, target);
		}
	}

	private EffectScript getScript(final Castable castable) {
		return scripts.get(castable.getScriptClass());
	}
}
