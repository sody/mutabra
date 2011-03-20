package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelQuery extends CodedEntityQuery<Level, LevelQuery> implements LevelFilter {
	private Long rating;

	public LevelQuery() {
		super(Level.class);
	}

	public LevelQuery withRating(final Long rating) {
		this.rating = rating;
		return query();
	}

	@Override
	public Long getRating() {
		return rating;
	}
}
