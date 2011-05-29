package com.mutabra.services;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface CodedEntityFilter<E extends CodedEntity>
		extends BaseEntityFilter<E> {

	String getCode();

}
