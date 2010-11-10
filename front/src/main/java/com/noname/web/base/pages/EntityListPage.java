/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.base.pages;

import com.noname.domain.BaseEntity;
import com.noname.web.services.StateConstants;
import org.greatage.tapestry.grid.EntityDataSource;

/**
 * @author Ivan Khalopik
 */
public abstract class EntityListPage<E extends BaseEntity>
		extends AbstractEntityPage<E> {

	private E row;

	private EntityDataSource<Long, E> dataSource;

	public E getRow() {
		return row;
	}

	public void setRow(E row) {
		this.row = row;
	}

	public EntityDataSource<Long, E> getDataSource() {
		if (dataSource == null) {
			dataSource = new EntityDataSource<Long, E>(getEntityService());
		}
		return dataSource;
	}

	protected abstract Object getDetails(String state, Long recordId);

	protected Object onAdd() {
		return getDetails(StateConstants.ADD_STATE, null);
	}

	protected Object onEdit(long id) {
		return getDetails(StateConstants.EDIT_STATE, id);
	}

	protected Object onView(long id) {
		return getDetails(StateConstants.VIEW_STATE, id);
	}

	protected void onDelete(long id) {
		final E entity = get(id);
		delete(entity);
	}

}