package com.mutabra.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.EntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface BaseEntityFilter<E extends BaseEntity> extends EntityFilter<Long, E> {
}
