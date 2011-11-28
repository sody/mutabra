package com.mutabra.web.components.game;

import com.mutabra.domain.game.BattleMember;
import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MemberDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter(required = true, allowNull = false)
	private BattleMember value;

	@Parameter
	private boolean selected;

	@Inject
	private JavaScriptSupport support;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupClientId() {
		clientId = "d_" + value.getPosition().getId();
	}

	@AfterRender
	void renderScript() {
		support.addInitializerCall("description", new JSONObject()
				.put("id", getClientId())
				.put("fieldId", "f_" + value.getPosition().getId())
				.put("actionsId", "a_" + value.getPosition().getId())
				.put("selected", selected));
	}
}
