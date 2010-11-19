package com.noname.services.common;

import com.noname.domain.common.Level;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelServiceImpl
		extends CodedEntityServiceImpl<Level, LevelQuery>
		implements LevelService {

	private static final String NEWBIE_LEVEL_CODE = "NEWBIE";

	public LevelServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Level.class, LevelQuery.class);
	}

	public Level getDefaultLevel() {
		return get(NEWBIE_LEVEL_CODE);
	}
}
