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

	public void setState(String state) {
		this.state = state;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public E getRecord() {
		return record;
	}

	public void setRecord(E record) {
		this.record = record;
	}

	public boolean isEditable() {
		return StateConstants.ADD_STATE.equals(getState()) || StateConstants.EDIT_STATE.equals(getState());
	}

	protected abstract Object getList();

	protected void onActivate(String state, Long recordId) {
		setState(state);
		if (StateConstants.ADD_STATE.equals(state)) {
			setRecordId(null);
			setRecord(create());
		} else if (StateConstants.VIEW_STATE.equals(state) || StateConstants.EDIT_STATE.equals(state)) {
			setRecordId(recordId);
			setRecord(get(recordId));
		} else {
			setRecordId(null);
			setRecord(null);
		}
	}

	protected List<?> onPassivate() {
		return CollectionFactory.newList(getState(), getRecordId());
	}

	protected Object onCancel() {
		return getList();
	}

	protected void onAdd() {
		setRecordId(null);
		setState(StateConstants.ADD_STATE);
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
		return onCancel();
	}
}