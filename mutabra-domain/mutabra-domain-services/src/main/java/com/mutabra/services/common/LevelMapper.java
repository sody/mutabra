package com.mutabra.services.common;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.CodedEntityMapper;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LevelMapper<E extends BaseEntity> extends CodedEntityMapper<E> {
	public final PropertyMapper<Long, E, Long> rating = property("rating");

	public LevelMapper(final String path) {
		super(path);
	}
}
