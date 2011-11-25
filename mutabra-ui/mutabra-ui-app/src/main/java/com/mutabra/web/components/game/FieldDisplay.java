package com.mutabra.web.components.game;

import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.BattlePlace;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.BattleService;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class FieldDisplay {

	@Inject
	private AccountContext accountContext;

	@Inject
	private BattleService battleService;

	@Inject
	private JavaScriptSupport support;

	@Property
	private Battle battle;

	@Property
	private BattleMember you;

	@Property
	private BattleMember opponent;

	@Property
	private BattleMember member;

	private BattleCard card;

	@Property
	private String cardClientId;

	@Property
	private BattlePlace place;

	@Property
	private List<BattlePlace> battleField;

	private Map<String, BattleCard> cards = new HashMap<String, BattleCard>();

	public boolean isFriend() {
		return member.equals(you);
	}

	public BattleCard getCard() {
		return card;
	}

	public void setCard(final BattleCard card) {
		this.card = card;
		cardClientId = support.allocateClientId("card");
		cards.put(cardClientId, card);
	}

	@SetupRender
	void setupBattleField() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();

		battleField = battleService.getBattleField(hero, battle);
		for (BattlePlace battlePlace : battleField) {
			if (battlePlace.hasHero()) {
				if (battlePlace.isEnemySide()) {
					opponent = battlePlace.getMember();
				} else {
					you = battlePlace.getMember();
				}
			}
		}
	}

	@AfterRender
	void renderScript() {
		final JSONObject spec = new JSONObject("id", "battle-field");
		final JSONArray list = new JSONArray();
		for (Map.Entry<String, BattleCard> entry : cards.entrySet()) {
			final TargetType targetType = entry.getValue().getCard().getCard().getTargetType();
			list.put(new JSONObject()
					.put("id", entry.getKey())
					.put("massive", targetType.isMassive())
					.put("supports_enemy_side", targetType.supportsEnemySide())
					.put("supports_friend_side", targetType.supportsFriendSide())
					.put("supports_empty_point", targetType.supportsEmptyPoint())
					.put("supports_hero_point", targetType.supportsHeroPoint())
					.put("supports_summon_point", targetType.supportsSummonPoint())
			);
		}
		spec.put("cards", list);
		support.addInitializerCall("field", spec);
	}
}
