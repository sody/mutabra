package com.mutabra.scripts;

import com.mutabra.domain.common.Castable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface EffectScript {

	void execute(Castable castable, Object target);
}
