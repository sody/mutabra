package com.mutabra.web.base.pages;

import com.mutabra.domain.CodedEntity;
import com.mutabra.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class CodedEntityPage<E extends CodedEntity> extends EntityPage<E> {

	protected abstract CodedEntityService<E> getEntityService();

	@Override
	protected E get(final Long entityId) {
		return entityId != null ? getEntityService().get(entityId, getLocale()) : null;
	}
}
