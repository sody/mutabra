package com.noname.web.base.pages;

import com.noname.domain.CodedEntity;
import com.noname.services.CodedEntityService;

/**
 * @author Ivan Khalopik
 */
public abstract class CodedEntityDetailsPage<E extends CodedEntity> extends EntityDetailsPage<E> {

	protected abstract CodedEntityService<E> getEntityService();

	@Override
	protected E get(Long entityId) {
		return entityId != null ? getEntityService().get(entityId, getLocale()) : null;
	}
}
