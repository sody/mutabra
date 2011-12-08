package com.mutabra.scripts;

import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.common.Effect;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ScriptExecutor {

	void executeScript(BattleUnit caster, Effect effect, List<?> targets);

}
