package com.mutabra.web.components.admin;

import com.mutabra.domain.security.Role;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.components.Dialog;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;

import static org.apache.tapestry5.EventConstants.PREPARE_FOR_SUBMIT;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RoleDialog extends AbstractComponent implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@InjectComponent
	private Dialog dialog;

	private Role value;

	public String getClientId() {
		return clientId;
	}

	public String getDialogClientId() {
		return clientId + "_dialog";
	}

	public String getTitle() {
		return getMessages().get(value.isNew() ? "add.title" : "edit.title");
	}

	public Role getValue() {
		return value;
	}

	public Object show(final Role role) {
		value = role;
		return dialog;
	}

	@OnEvent(PREPARE_FOR_SUBMIT)
	void prepare(final Role role) {
		value = role;
	}
}
