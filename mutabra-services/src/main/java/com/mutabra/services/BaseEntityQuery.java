package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityQuery<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends EntityQuery<Long, E, Q>
		implements BaseEntityFilter<E> {

	public BaseEntityQuery(final Class<E> entityClass) {
		super(entityClass);
	}
}
