package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = Tables.LEVEL)
public class LevelImpl extends CodedEntityImpl implements Level {

	@Column(name = "RATING", nullable = false)
	private long rating;

	public LevelImpl() {
		super(Tables.LEVEL, TranslationType.STANDARD);
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
