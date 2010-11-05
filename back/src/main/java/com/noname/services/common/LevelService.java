package com.noname.services.common;

import com.noname.domain.common.Level;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 */
public interface LevelService extends CodedEntityService<Level> {

	Level getDefaultLevel();

}
