package com.mutabra.web.components.game;

import com.mutabra.domain.game.BattleMember;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MemberDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter(required = true, allowNull = false)
	private BattleMember value;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupClientId() {
		clientId = "f_" + value.getPosition().getId() + "_description";
	}
}
