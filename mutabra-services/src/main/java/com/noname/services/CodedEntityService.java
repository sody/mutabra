package com.noname.services;

import com.mutabra.domain.CodedEntity;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CodedEntityService<E extends CodedEntity>
		extends BaseEntityService<E> {

	E get(final Long pk, final Locale locale);

	E get(final String code);

	E get(final String code, final Locale locale);

}
