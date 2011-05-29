package com.mutabra.services;

import com.mutabra.domain.CodedEntity;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityServiceImpl<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends BaseEntityServiceImpl<E, Q>
		implements CodedEntityService<E> {

	public CodedEntityServiceImpl(final EntityRepository repository,
								  final Class<E> entityClass,
								  final Class<Q> queryClass) {
		super(repository, entityClass, queryClass);
	}

	public E get(final String code) {
		if (code == null) {
			return null;
		}
		return createQuery().withCode(code).unique();
	}
}
