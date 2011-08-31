package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityQuery;
import org.greatage.domain.EntityQueryImpl;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityQuery<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends EntityQueryImpl<Long, E, Q>
		implements BaseEntityFilter<E>, EntityQuery<Long, E, Q> {

	protected BaseEntityQuery(final EntityRepository repository, final Class<E> entityClass) {
		super(repository, entityClass);
	}
}
