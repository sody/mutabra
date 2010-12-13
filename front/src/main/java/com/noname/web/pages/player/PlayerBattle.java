package com.noname.web.pages.player;

import com.noname.game.Battle;
import com.noname.game.BattleService;
import com.noname.game.Player;
import com.noname.services.security.AuthorityConstants;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
@Allow(AuthorityConstants.STATUS_PLAYING)
public class PlayerBattle extends AbstractPage {

	@Inject
	private BattleService battleService;

	private Battle battle;
	private Player player;

	public Battle getBattle() {
		return battle;
	}

	public Player getPlayer() {
		return player;
	}

	void setupRender() {
		battle = battleService.getCurrentBattle();
		player = battleService.getCurrentPlayer();
	}
}
