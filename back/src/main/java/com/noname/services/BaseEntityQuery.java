package com.noname.services;

import com.noname.domain.BaseEntity;
import org.greatage.domain.EntityQuery;

/**
 * @author Ivan Khalopik
 */
public class BaseEntityQuery<E extends BaseEntity, Q extends BaseEntityQuery<E, Q>>
		extends EntityQuery<Long, E, Q>
		implements BaseEntityFilter<E> {

	public BaseEntityQuery(final Class<E> entityClass) {
		super(entityClass);
	}
}
