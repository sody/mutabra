package com.mutabra.domain;

import org.greatage.domain.AbstractEntity;

import javax.persistence.Id;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityImpl extends AbstractEntity<Long> implements BaseEntity {

	@Id
	private Long id;

	public Long getId() {
		return id;
	}
}
