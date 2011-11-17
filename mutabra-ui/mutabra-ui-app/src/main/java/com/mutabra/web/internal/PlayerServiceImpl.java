package com.mutabra.web.internal;

import com.mutabra.domain.player.Hero;
import com.mutabra.web.services.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PlayerServiceImpl implements PlayerService {
	private static final long EXPIRATION_TIMEOUT = 100000l;

	private final Map<Hero, Long> timestamps = new ConcurrentHashMap<Hero, Long>();

	public void update(final Hero hero) {
		timestamps.put(hero, System.currentTimeMillis());
	}

	public List<Hero> getPlayers() {
		final long time = System.currentTimeMillis();
		final ArrayList<Hero> toDelete = new ArrayList<Hero>();
		for (Map.Entry<Hero, Long> entry : timestamps.entrySet()) {
			if (time - entry.getValue() > EXPIRATION_TIMEOUT) {
				toDelete.add(entry.getKey());
			}
		}
		for (Hero hero : toDelete) {
			timestamps.remove(hero);
		}
		return new ArrayList<Hero>(timestamps.keySet());
	}
}
