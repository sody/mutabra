package com.mutabra.services.common;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.CodedEntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FaceMapper<E extends BaseEntity> extends CodedEntityMapper<E> {

	public FaceMapper(final String path) {
		super(path);
	}
}
