package com.noname.services.common;

import com.mutabra.domain.common.Level;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelService extends CodedEntityService<Level> {

	Level getDefaultLevel();

}
