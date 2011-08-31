package com.mutabra.services.player;

import com.mutabra.domain.player.Hero;
import com.mutabra.services.BaseEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroQuery extends BaseEntityQuery<Hero, HeroQuery> {

	public HeroQuery(final EntityRepository repository) {
		super(repository, Hero.class);
	}
}
