package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
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
public class CardDescription extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private Card value;

	@Inject
	private JavaScriptSupport support;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public boolean isEffectCard() {
		return value.getType() == CardType.EFFECT;
	}

	public boolean isSummonCard() {
		return value.getType() == CardType.SUMMON;
	}

	@SetupRender
	void setupClientId() {
		clientId = "d_" + value.getCode();
	}

	@AfterRender
	void renderScript() {
		support.addInitializerCall("description", new JSONObject()
				.put("id", getClientId())
				.put("cardId", "c_" + value.getCode()));
	}
}
