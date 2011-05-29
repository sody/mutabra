package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityServiceImpl;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelServiceImpl
		extends CodedEntityServiceImpl<Level, LevelQuery>
		implements LevelService {

	public LevelServiceImpl(final EntityRepository repository) {
		super(repository, Level.class, LevelQuery.class);
	}

	public Level getDefaultLevel() {
		return get(NEWBIE_LEVEL_CODE);
	}
}
