package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Level extends CodedEntity {

	String RATING_PROPERTY = "rating";

	long getRating();

	void setRating(long rating);
}
