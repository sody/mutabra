package com.mutabra.domain;

import com.google.appengine.api.datastore.Key;
import org.greatage.domain.AbstractEntity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityImpl extends AbstractEntity<Long> implements BaseEntity {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	public Key getKey() {
		return key;
	}

	public Long getId() {
		return key != null ? key.getId() : null;
	}
}
