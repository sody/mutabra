package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
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

	public boolean isEffectCard() {
		return value.getType() == CardType.EFFECT;
	}

	public boolean isSummonCard() {
		return value.getType() == CardType.SUMMON;
	}

	@SetupRender
	void setupClientId() {
		clientId = "c_" + value.getCode() + "_description";
	}
}
