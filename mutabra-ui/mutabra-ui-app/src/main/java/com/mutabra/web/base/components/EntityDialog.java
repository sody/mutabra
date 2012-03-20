package com.mutabra.web.base.components;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.components.Modal;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import static org.apache.tapestry5.EventConstants.FAILURE;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EntityDialog<E extends BaseEntity> extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@InjectComponent
	private Modal modal;

	@InjectComponent
	private Zone formZone;

	@InjectComponent
	private Form form;

	@Inject
	private Block modalBlock;

	private E value;

	public String getClientId() {
		return clientId;
	}

	public String getModalId() {
		return clientId + "_modal";
	}

	public String getFormZoneId() {
		return clientId + "_form_zone";
	}

	public String getTitle() {
		return getMessages().get(value.isNew() ? "add.title" : "edit.title");
	}

	public E getValue() {
		return value;
	}

	public Object show(final E entity) {
		init(entity);
		form.clearErrors();
		return modalBlock;
	}

	protected void init(final E entity) {
		value = entity;
	}

	@OnEvent(value = FAILURE)
	public Object onFailure() {
		return formZone;
	}
}
