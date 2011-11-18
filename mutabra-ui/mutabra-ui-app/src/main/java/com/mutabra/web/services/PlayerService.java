package com.mutabra.web.services;

import com.mutabra.domain.game.Hero;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface PlayerService {

	void update(Hero hero);

	List<Hero> getPlayers();
}
