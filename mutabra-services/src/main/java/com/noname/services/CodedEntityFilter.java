package com.noname.services;

import com.mutabra.domain.CodedEntity;

/**
 * @author Ivan Khalopik
 */
public interface CodedEntityFilter<E extends CodedEntity>
		extends BaseEntityFilter<E> {

	String getCode();

}
