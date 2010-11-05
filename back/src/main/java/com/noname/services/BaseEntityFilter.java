package com.noname.services;

import com.noname.domain.BaseEntity;
import ga.domain.repository.GenericEntityFilter;

/**
 * @author Ivan Khalopik
 */
public interface BaseEntityFilter<E extends BaseEntity> extends GenericEntityFilter<E> {
}
