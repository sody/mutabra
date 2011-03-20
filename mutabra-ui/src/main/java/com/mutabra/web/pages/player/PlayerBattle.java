package com.mutabra.web.pages.player;

import com.mutabra.game.*;
import com.mutabra.services.security.GameSecurityContext;
import com.mutabra.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
//@Allow(AuthorityConstants.STATUS_PLAYING)
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
