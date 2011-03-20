package com.mutabra.services.player;

import com.mutabra.domain.player.Hero;
import com.mutabra.services.BaseEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroQuery extends BaseEntityQuery<Hero, HeroQuery> implements HeroFilter {
	public HeroQuery() {
		super(Hero.class);
	}
}
