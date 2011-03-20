package com.noname.web.pages.player;

import com.noname.game.Battle;
import com.noname.game.BattleService;
import com.noname.game.Player;
import com.noname.services.security.AuthorityConstants;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

import java.util.Collection;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
//@Allow(AuthorityConstants.STATUS_PLAYING)
public class PlayerBattle extends AbstractPage {

	@Inject
	private BattleService battleService;

	@Property
	private Battle battle;

	@Property
	private Player player;

	private Collection<Player> players;

	public Collection<Player> getPlayers() {
		return players;
	}

	void setupRender() {
		battle = battleService.getCurrentBattle();
		players = battle.getAllPlayers();
	}
}
