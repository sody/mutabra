package com.mutabra.web.base.pages;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractEntityPage<E extends BaseEntity>
		extends AbstractPage {

	protected abstract BaseEntityService<E> getEntityService();

	protected E get(final Long entityId) {
		return entityId != null ? getEntityService().get(entityId) : null;
	}

	protected E create() {
		return getEntityService().create();
	}

	protected void save(final E entity) {
		getEntityService().saveOrUpdate(entity);
	}

	protected void delete(final E entity) {
		getEntityService().delete(entity);
	}
}
