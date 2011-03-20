package com.noname.services;

import com.noname.domain.BaseEntity;
import org.greatage.domain.EntityFilter;

/**
 * @author Ivan Khalopik
 */
public interface BaseEntityFilter<E extends BaseEntity> extends EntityFilter<Long, E> {
}
