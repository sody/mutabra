package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.EntityServiceImpl;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityServiceImpl<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends EntityServiceImpl<Long, E, Q>
		implements BaseEntityService<E> {

	public BaseEntityServiceImpl(final EntityRepository repository,
								 final Class<E> entityClass,
								 final Class<Q> queryClass) {
		super(repository, entityClass, queryClass);
	}
}
