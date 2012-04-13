package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CardDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private Card value;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupCard() {
		clientId = "description_" + value.getCode();
	}
}
