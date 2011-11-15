package com.mutabra.services.common;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.CodedEntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardMapper<E extends BaseEntity> extends CodedEntityMapper<E> {

	public CardMapper(final String path) {
		super(path);
	}
}
