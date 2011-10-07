package com.mutabra.web.base.components;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.components.Dialog;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityDialog<E extends BaseEntity> extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@InjectComponent
	private Dialog dialog;

	private E value;

	public String getClientId() {
		return clientId;
	}

	public String getDialogClientId() {
		return clientId + "_dialog";
	}

	public String getTitle() {
		return getMessages().get(value.isNew() ? "add.title" : "edit.title");
	}

	public E getValue() {
		return value;
	}

	public Object show(final E entity) {
		init(entity);
		return dialog;
	}

	protected void init(final E entity) {
		value = entity;
	}
}
