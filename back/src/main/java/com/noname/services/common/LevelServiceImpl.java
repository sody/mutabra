package com.noname.services.common;

import com.noname.domain.common.Level;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class LevelServiceImpl
		extends CodedEntityServiceImpl<Level, LevelQuery>
		implements LevelService {

	private static final long DEFAULT_LEVEL_ID = 1l;

	public LevelServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Level.class, LevelQuery.class);
	}

	@Override
	public Level getDefaultLevel() {
		return get(DEFAULT_LEVEL_ID);
	}
}
