/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.base.pages;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.services.StateConstants;
import org.greatage.tapestry.grid.EntityDataSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class EntityListPage<E extends BaseEntity>
		extends AbstractEntityPage<E> {

	private E row;

	private EntityDataSource<Long, E> dataSource;

	public E getRow() {
		return row;
	}

	public void setRow(final E row) {
		this.row = row;
	}

	public EntityDataSource<Long, E> getDataSource() {
		if (dataSource == null) {
			dataSource = new EntityDataSource<Long, E>(getEntityService());
		}
		return dataSource;
	}

	protected abstract Object getDetails(final String state, final Long recordId);

	protected Object onAdd() {
		return getDetails(StateConstants.EDIT_STATE, null);
	}

	protected Object onEdit(final long id) {
		return getDetails(StateConstants.EDIT_STATE, id);
	}

	protected Object onView(final long id) {
		return getDetails(StateConstants.VIEW_STATE, id);
	}

	protected void onDelete(final long id) {
		final E entity = get(id);
		delete(entity);
	}

}