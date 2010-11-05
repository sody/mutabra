package com.noname.services;

import com.noname.domain.BaseEntity;
import ga.domain.repository.EntityRepository;
import ga.domain.services.GenericEntityServiceImpl;

/**
 * @author Ivan Khalopik
 */
public class BaseEntityServiceImpl<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends GenericEntityServiceImpl<E, Q>
		implements BaseEntityService<E> {

	public BaseEntityServiceImpl(final EntityRepository repository,
								 final Class<E> entityClass,
								 final Class<Q> filterClass) {
		super(repository, entityClass, filterClass);
	}
}
