package com.noname.services;

import com.noname.domain.CodedEntity;
import ga.domain.i18n.TranslatableEntityQuery;

/**
 * @author Ivan Khalopik
 */
public class CodedEntityQuery<E extends CodedEntity, Q extends CodedEntityQuery<E, Q>>
		extends TranslatableEntityQuery<E, Q>
		implements CodedEntityFilter<E> {

	public CodedEntityQuery(final Class<E> entityClass) {
		super(entityClass);
	}
}
