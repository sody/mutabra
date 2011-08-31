package com.mutabra.services.common;

import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceQuery extends CodedEntityQuery<Race, RaceQuery> {

	public RaceQuery(final EntityRepository repository) {
		super(repository, Race.class);
	}
}
