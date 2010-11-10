package com.noname.services;

import com.noname.domain.BaseEntity;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.EntityServiceImpl;

/**
 * @author Ivan Khalopik
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
