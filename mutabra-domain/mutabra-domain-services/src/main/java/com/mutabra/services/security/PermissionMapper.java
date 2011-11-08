package com.mutabra.services.security;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.CodedEntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PermissionMapper<E extends BaseEntity> extends CodedEntityMapper<E> {

	public PermissionMapper(final String path) {
		super(path);
	}
}
