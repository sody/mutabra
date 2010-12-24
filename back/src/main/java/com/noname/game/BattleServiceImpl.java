package com.noname.game;

import com.noname.domain.player.Hero;
import com.noname.services.security.GameSecurityContext;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class BattleServiceImpl implements BattleService {
	private final GameSecurityContext securityContext;

	private final Map<String, BattlePlayer> players = CollectionUtils.newConcurrentMap();

	public BattleServiceImpl(final GameSecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public void createDuel(final String name, final String rivalName) {
		if (getPlayer(name) != null) {
			throw new IllegalStateException(String.format("Already in battle: %s", name));
		}
		if (getPlayer(rivalName) != null) {
			throw new IllegalStateException(String.format("Already in battle: %s", rivalName));
		}
		final Duel duel = new Duel();
		duel.getFirstCommand().addPlayer(name, getHero(name));
		duel.getSecondCommand().addPlayer(rivalName, getHero(rivalName));

		players.put(name, duel.getFirstCommand().getPlayer(name));
		players.put(rivalName, duel.getSecondCommand().getPlayer(rivalName));
	}

	@Override
	public BattlePlayer getPlayer(final String name) {
		return players.get(name);
	}

	private Hero getHero(String name) {
		final User user = securityContext.getUser(name);
		return user.getHero();
	}
}
