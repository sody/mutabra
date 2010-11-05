package com.noname.services;

import com.noname.domain.BaseEntity;
import ga.domain.repository.GenericEntityQuery;

/**
 * @author Ivan Khalopik
 */
public class BaseEntityQuery<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends GenericEntityQuery<E, Q>
		implements BaseEntityFilter<E> {

	public BaseEntityQuery(final Class<E> entityClass) {
		super(entityClass);
	}
}
