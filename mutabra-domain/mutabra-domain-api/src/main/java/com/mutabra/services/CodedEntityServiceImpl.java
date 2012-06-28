package com.mutabra.services;

import com.mutabra.domain.CodedEntity;
import org.greatage.domain.Repository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityServiceImpl<E extends CodedEntity> extends BaseEntityServiceImpl<E> implements CodedEntityService<E> {
	private final CodedEntityMapper<E> entity$ = new CodedEntityMapper<E>(null);
	private final Class<? extends E> realEntityClass;

	public CodedEntityServiceImpl(final Repository repository, final Class<E> entityClass) {
		super(repository, entityClass);
		//noinspection unchecked
		realEntityClass = (Class<? extends E>) repository.create(entityClass).getClass();
	}

	public E get(final String code) {
		//noinspection unchecked
		return code != null ? query().filter(entity$.code.eq(code)).unique() : null;
	}

	public E create(final String code) {
		return ReflectionUtils.newInstance(realEntityClass, code);
	}
}
