/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.base.pages;

import com.noname.domain.BaseEntity;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.greatage.tapestry.grid.EntityDataSource;

/**
 * @author Ivan Khalopik
 */
public abstract class EntityPage<E extends BaseEntity>
		extends EntityDetailsPage<E> {

	private E row;

	private EntityDataSource<Long, E> dataSource;

	@Component
	private Form detailsForm;

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

	protected void onView(long id) {
		setRecordId(id);
	}

	protected void onEdit(long id) {
		setRecordId(id);
	}

	protected void onDelete(long id) {
		final E entity = get(id);
		setRecord(entity);
	}

	protected Object getList() {
		setRecordId(null);
		setState(null);
		return null;
	}
}