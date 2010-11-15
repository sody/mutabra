/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.base.pages;

import com.noname.domain.BaseEntity;
import com.noname.web.services.StateConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class EntityDetailsPage<E extends BaseEntity>
		extends AbstractEntityPage<E> {

	private String state;
	private Long recordId;

	private E record;

	@Component
	private Form detailsForm;

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(final Long recordId) {
		this.recordId = recordId;
	}

	public E getRecord() {
		return record;
	}

	public void setRecord(final E record) {
		this.record = record;
	}

	protected abstract Object getListPage();

	protected void onActivate(final String state, final Long recordId) {
		setState(state);
		setRecordId(recordId);
		setRecord(recordId != null ? get(recordId) : create());
	}

	protected List<?> onPassivate() {
		return CollectionFactory.newList(getState(), getRecordId());
	}

	protected Object onCancel() {
		return getListPage();
	}

	protected void onAdd() {
		setRecordId(null);
		setState(StateConstants.EDIT_STATE);
	}

	protected void onEdit() {
		setState(StateConstants.EDIT_STATE);
	}

	protected void onView() {
		setState(StateConstants.VIEW_STATE);
	}

	protected void onSave() {
		if (detailsForm.isValid()) {
			save(getRecord());
			setRecordId(getRecord().getId());
			onView();
		}
	}

	protected Object onDelete() {
		delete(getRecord());
		return getListPage();
	}
}