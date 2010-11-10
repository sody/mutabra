package com.noname.services;

import com.noname.domain.CodedEntity;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public interface CodedEntityService<E extends CodedEntity>
		extends BaseEntityService<E> {

	E get(final Long pk, final Locale locale);

}
