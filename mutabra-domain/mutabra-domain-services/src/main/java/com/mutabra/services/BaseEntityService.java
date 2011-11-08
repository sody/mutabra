package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BaseEntityService<E extends BaseEntity>
		extends EntityService<Long, E> {
}
