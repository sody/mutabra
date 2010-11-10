package com.noname.services;

import com.noname.domain.BaseEntity;
import org.greatage.domain.EntityService;

/**
 * @author Ivan Khalopik
 */
public interface BaseEntityService<E extends BaseEntity>
		extends EntityService<Long, E> {
}
