package com.mutabra.services;

import com.mutabra.domain.CodedEntity;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityQuery<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends BaseEntityQuery<E, Q>
		implements CodedEntityFilter<E> {

	private String code;

	protected CodedEntityQuery(final EntityRepository repository, final Class<E> entityClass) {
		super(repository, entityClass);
	}

	public String getCode() {
		return code;
	}

	public Q withCode(final String code) {
		this.code = code;
		return query();
	}
}
