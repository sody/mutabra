package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleHero;
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
public class SkipTurnDisplay extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private BattleHero hero;

	@Inject
	private JavaScriptSupport support;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupButton() {
		clientId = support.allocateClientId("skipTurnButton");
	}

	@AfterRender
	void renderScript() {
		support.addInitializerCall("skipButton", new JSONObject()
				.put("id", getClientId())
				.put("url", getResources().createEventLink("skipTurn", hero).toAbsoluteURI())
		);
	}
}
