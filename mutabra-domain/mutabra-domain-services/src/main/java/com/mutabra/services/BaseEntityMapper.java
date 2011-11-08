package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityMapper;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityMapper<E extends BaseEntity> extends EntityMapper<Long, E> {
	public final PropertyMapper<Long, E, Long> id = property("id");

	public BaseEntityMapper(final String path) {
		super(path);
	}
}
