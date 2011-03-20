/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.base.pages;

import com.mutabra.domain.BaseEntity;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.greatage.tapestry.grid.EntityDataSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
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

	public void setRow(final E row) {
		this.row = row;
	}

	public EntityDataSource<Long, E> getDataSource() {
		if (dataSource == null) {
			dataSource = new EntityDataSource<Long, E>(getEntityService());
		}
		return dataSource;
	}

	protected void onView(final long id) {
		setRecordId(id);
	}

	protected void onEdit(final long id) {
		setRecordId(id);
	}

	protected void onDelete(final long id) {
		final E entity = get(id);
		setRecord(entity);
	}

	protected Object getListPage() {
		setRecordId(null);
		setState(null);
		return null;
	}
}