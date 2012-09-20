package com.mutabra.services.game;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityMapper;
import org.greatage.domain.PropertyMapper;

import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroMapper<E extends BaseEntity> extends BaseEntityMapper<E> {
	public final PropertyMapper<Long, E, Date> lastActive$ = property("lastActive");

	public HeroMapper(final String path) {
		super(path);
	}
}
