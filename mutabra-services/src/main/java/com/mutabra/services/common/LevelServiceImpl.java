package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.TranslationService;
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
