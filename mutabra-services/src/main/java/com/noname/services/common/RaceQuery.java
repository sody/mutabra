package com.noname.services.common;

import com.noname.domain.common.Race;
import com.noname.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceQuery extends CodedEntityQuery<Race, RaceQuery> implements RaceFilter {

	public RaceQuery() {
		super(Race.class);
	}

}
