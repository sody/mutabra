package com.mutabra.domain.common;

import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@PersistenceCapable
public class LevelImpl extends CodedEntityImpl implements Level {

	@Persistent
	private long rating;

	public LevelImpl() {
		super("LEVEL", TranslationType.STANDARD);
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
