package com.mutabra.services.player;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroMapper<E extends BaseEntity> extends BaseEntityMapper<E> {

	public HeroMapper(final String path) {
		super(path);
	}
}
