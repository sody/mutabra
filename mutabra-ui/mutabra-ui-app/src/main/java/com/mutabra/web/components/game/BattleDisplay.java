package com.mutabra.web.components.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleField;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.BattleService;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleDisplay {

	@Inject
	private AccountContext accountContext;

	@Inject
	private BattleService battleService;

	@Property
	private Battle battle;

	@Property
	private BattleMember you;

	@Property
	private BattleMember opponent;

	@Property
	private BattleField field;

	@Property
	private List<BattleField> fields;

	@Property
	private BattleMember member;

	@Property
	private BattleCard card;

	public boolean isSelected() {
		return member.equals(you);
	}

	@SetupRender
	void setupBattleField() {
		final Hero hero = accountContext.getHero();
		battle = accountContext.getBattle();

		fields = battleService.getBattleField(hero, battle);
		for (BattleField battleField : fields) {
			if (battleField.hasHero()) {
				if (battleField.isEnemySide()) {
					opponent = battleField.getMember();
				} else {
					you = battleField.getMember();
				}
			}
		}
	}
}
