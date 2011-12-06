package com.mutabra.web.components.game;

import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.TargetType;
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
public class CardDisplay extends AbstractComponent implements ClientElement {

	@Property
	@Parameter
	private BattleCard value;

	@Property
	private Card card;

	@Inject
	private JavaScriptSupport support;

	private String clientId;

	public String getClientId() {
		return clientId;
	}

	@SetupRender
	void setupCard() {
		card = value.getCard().getCard();
		clientId = "c_" + card.getCode();
	}

	@AfterRender
	void renderScript() {
		final TargetType targetType = card.getTargetType();
		support.addInitializerCall("card", new JSONObject()
				.put("id", getClientId())
				.put("url", getResources().createEventLink("registerActions", value).toAbsoluteURI())
				.put("massive", targetType.isMassive())
				.put("supports_enemy_side", targetType.supportsEnemySide())
				.put("supports_friend_side", targetType.supportsFriendSide())
				.put("supports_empty_point", targetType.supportsEmptyPoint())
				.put("supports_hero_point", targetType.supportsHeroPoint())
				.put("supports_summon_point", targetType.supportsSummonPoint()));
	}
}
