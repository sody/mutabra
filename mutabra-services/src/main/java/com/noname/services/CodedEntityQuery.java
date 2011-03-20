package com.noname.services;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityQuery<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends BaseEntityQuery<E, Q>
		implements CodedEntityFilter<E> {

	private String code;

	public CodedEntityQuery(final Class<E> entityClass) {
		super(entityClass);
	}

	@Override
	public String getCode() {
		return code;
	}

	Q withCode(final String code) {
		this.code = code;
		return query();
	}
}
