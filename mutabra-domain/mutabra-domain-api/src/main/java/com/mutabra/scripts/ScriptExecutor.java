package com.mutabra.scripts;

import com.mutabra.domain.common.Castable;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ScriptExecutor {

	void executeScript(Castable castable, List<?> targets);

}
