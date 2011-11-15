package com.mutabra.services.security;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ChangeSetMapper<E extends BaseEntity> extends BaseEntityMapper<E> {

	public ChangeSetMapper(final String path) {
		super(path);
	}
}
