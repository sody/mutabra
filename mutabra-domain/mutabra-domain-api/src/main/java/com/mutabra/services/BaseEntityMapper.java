package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityMapper<E extends BaseEntity> extends EntityMapper<Long, E> {
	public BaseEntityMapper(final String path) {
		super(path);
	}
}
