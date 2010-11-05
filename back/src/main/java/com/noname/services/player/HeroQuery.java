package com.noname.services.player;

import com.noname.domain.player.Hero;
import com.noname.services.BaseEntityQuery;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroQuery extends BaseEntityQuery<Hero, HeroQuery> implements HeroFilter {
	public HeroQuery() {
		super(Hero.class);
	}
}
