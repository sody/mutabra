package com.mutabra.web.components.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.BattlePlace;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.BattleService;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;

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
	private BattlePlace field;

	@Property
	private List<BattlePlace> fields;

	@Property
	private BattleMember member;

	@Property
	private BattleCard card;

	public boolean isSelected() {
		return field.getPosition().equals(you.getPosition());
	}

	public boolean isVisible() {
		return member.equals(you);
	}

	public String getMemberClientId() {
		return "f_" + member.getPosition().getId() + "_info";
	}

	@SetupRender
	void setupBattleField() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();

		fields = battleService.getBattleField(hero, battle);
		for (BattlePlace battlePlace : fields) {
			if (battlePlace.hasHero()) {
				if (battlePlace.isEnemySide()) {
					opponent = battlePlace.getMember();
				} else {
					you = battlePlace.getMember();
				}
			}
		}
	}
}
