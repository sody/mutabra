package com.mutabra.web.components.game;

import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.CardType;
import com.mutabra.domain.common.TargetType;
import org.apache.tapestry5.BindingConstants;
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
public class CardDisplay implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter
	private Card value;

	@Property
	@Parameter
	private boolean visible;

	@Inject
	private JavaScriptSupport support;

	private String assignedClientId;

	public String getClientId() {
		return assignedClientId;
	}

	public boolean isEffectCard() {
		return value.getType() == CardType.EFFECT;
	}

	public boolean isSummonCard() {
		return value.getType() == CardType.SUMMON;
	}

	@SetupRender
	void setupClientId() {
		assignedClientId = support.allocateClientId(clientId);
	}

	@AfterRender
	void renderScript() {
		if (visible) {
			final JSONObject spec = new JSONObject("id", getClientId());
			final TargetType targetType = value.getTargetType();
			spec.put("target_type",
					new JSONObject()
							.put("massive", targetType.isMassive())
							.put("supports_enemy_side", targetType.supportsEnemySide())
							.put("supports_friend_side", targetType.supportsFriendSide())
							.put("supports_empty_point", targetType.supportsEmptyPoint())
							.put("supports_hero_point", targetType.supportsHeroPoint())
							.put("supports_summon_point", targetType.supportsSummonPoint())
			);
			support.addInitializerCall("card", spec);
		}
	}
}
