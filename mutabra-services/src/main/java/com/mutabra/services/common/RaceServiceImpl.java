package com.mutabra.services.common;

import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityServiceImpl;
import com.mutabra.services.TranslationService;
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
