package com.noname.services;

import com.mutabra.domain.BaseEntity;
import org.greatage.domain.AbstractFilterProcessor;
import org.greatage.domain.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class BaseEntityFilterProcessor<E extends BaseEntity, F extends BaseEntityFilter<E>>
		extends AbstractFilterProcessor<Long, E, F> {

	/**
	 * Constructor with supported entity class initialization.
	 *
	 * @param supportedEntityClass supported entity class
	 */
	protected BaseEntityFilterProcessor(final Class<? extends Entity> supportedEntityClass) {
		super(supportedEntityClass);
	}

}
