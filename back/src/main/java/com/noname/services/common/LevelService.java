package com.noname.services.common;

import com.noname.domain.common.Level;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelService extends CodedEntityService<Level> {

	Level getDefaultLevel();

}
