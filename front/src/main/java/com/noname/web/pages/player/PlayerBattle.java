package com.noname.web.pages.player;

import com.noname.game.Battle;
import com.noname.game.BattleCommand;
import com.noname.game.BattlePlayer;
import com.noname.game.BattleService;
import com.noname.game.Duel;
import com.noname.game.User;
import com.noname.services.security.AuthorityConstants;
import com.noname.services.security.GameSecurityContext;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Allow;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(AuthorityConstants.STATUS_PLAYING)
public class PlayerBattle extends AbstractPage {

	@Inject
	private BattleService battleService;

	@Inject
	private GameSecurityContext securityContext;

	@Property
	private BattleCommand firstCommand;

	@Property
	private BattleCommand secondCommand;

	void setupRender() {
		final User currentUser = securityContext.getCurrentUser();
		final BattlePlayer player = battleService.getPlayer(currentUser.getName());
		final Battle battle = player.getCommand().getBattle();

		firstCommand = ((Duel) battle).getFirstCommand();
		secondCommand = ((Duel) battle).getSecondCommand();
	}
}
