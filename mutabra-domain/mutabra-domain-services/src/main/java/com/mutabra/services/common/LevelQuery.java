package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelQuery extends CodedEntityQuery<Level, LevelQuery> {
	private Long rating;

	public LevelQuery(EntityRepository repository) {
		super(repository, Level.class);
	}

	public LevelQuery withRating(final Long rating) {
		this.rating = rating;
		return query();
	}

	public Long getRating() {
		return rating;
	}
}
