package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.EntityServiceImpl;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseEntityServiceImpl<E extends BaseEntity>
		extends EntityServiceImpl<Long, E>
		implements BaseEntityService<E> {

	private final EntityRepository repository;

	public BaseEntityServiceImpl(final EntityRepository repository,
								 final Class<E> entityClass) {
		super(repository, entityClass);

		this.repository = repository;
	}

	protected EntityRepository repository() {
		return repository;
	}
}
