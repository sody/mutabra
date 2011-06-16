package com.mutabra.web.base.pages;

import com.mutabra.domain.CodedEntity;
import com.mutabra.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class CodedEntityDetailsPage<E extends CodedEntity> extends EntityDetailsPage<E> {

	protected abstract CodedEntityService<E> getEntityService();

	@Override
	protected E get(final Long entityId) {
		return super.get(entityId);
	}
}
