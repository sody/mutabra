package com.mutabra.services.common;

import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceQuery extends CodedEntityQuery<Race, RaceQuery> implements RaceFilter {

	public RaceQuery() {
		super(Race.class);
	}

}
