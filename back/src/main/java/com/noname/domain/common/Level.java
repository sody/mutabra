package com.noname.domain.common;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "LEVEL")
public class Level extends CodedEntity {

	@Column(name = "RATING", nullable = false)
	private long rating;

	public Level() {
		super("LEVEL", TranslationType.STANDARD);
	}

	public long getRating() {
		return rating;
	}

	public void setRating(final long rating) {
		this.rating = rating;
	}
}
