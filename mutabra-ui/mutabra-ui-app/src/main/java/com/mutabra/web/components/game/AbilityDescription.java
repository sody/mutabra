package com.mutabra.web.components.game;

import com.mutabra.domain.common.Ability;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbilityDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private Ability value;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupCard() {
		clientId = "a_" + value.getCode() + "_description";
	}
}
