package com.noname.services.common;

import com.noname.domain.common.Race;
import com.noname.services.CodedEntityServiceImpl;
import com.noname.services.TranslationService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceServiceImpl extends CodedEntityServiceImpl<Race, RaceQuery> implements RaceService {

	public RaceServiceImpl(final EntityRepository repository, final TranslationService translationService) {
		super(repository, translationService, Race.class, RaceQuery.class);
	}
}
