package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelService extends CodedEntityService<Level> {

	Level getDefaultLevel();

}
