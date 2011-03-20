package com.noname.game;

import com.noname.services.security.GameSecurityContext;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class BattleServiceImpl implements BattleService {
	private final Map<String, Battle> battles = CollectionUtils.newConcurrentMap();

	private final GameSecurityContext securityContext;

	public BattleServiceImpl(final GameSecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public Player getCurrentPlayer() {
		final User currentUser = securityContext.getCurrentUser();
		final Battle battle = getBattle(currentUser);
		return battle.getPlayer(currentUser.getName());
	}

	public Battle getCurrentBattle() {
		final User currentUser = securityContext.getCurrentUser();
		return getBattle(currentUser);
	}

	public Battle createDuel(final User user) {
		final User currentUser = securityContext.getCurrentUser();
		if (getBattle(currentUser) == null && getBattle(user) == null) {
			final Battle battle = new Battle(currentUser, user);
			addBattle(currentUser, battle);
			addBattle(user, battle);
			return battle;
		}
		return null;
	}

	private Battle getBattle(final User user) {
		return battles.get(user.getName());
	}

	private void addBattle(final User user, final Battle battle) {
		battles.put(user.getName(), battle);
	}
}
