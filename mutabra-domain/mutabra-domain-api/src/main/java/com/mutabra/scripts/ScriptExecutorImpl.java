package com.mutabra.scripts;

import com.mutabra.domain.common.Effect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScriptExecutorImpl implements ScriptExecutor {
    private final Map<String, EffectScript> scripts = new HashMap<String, EffectScript>();

    public ScriptExecutorImpl(final Collection<EffectScript> scripts) throws Exception {
        for (EffectScript script : scripts) {
            this.scripts.put(script.getClass().getName(), script);
        }
    }

    public void executeScript(final ScriptContext context) {
        final EffectScript script = getScript(context.getEffect());
        script.execute(context);
    }

    private EffectScript getScript(final Effect effect) {
        return scripts.get(effect.getScriptClass());
    }
}
