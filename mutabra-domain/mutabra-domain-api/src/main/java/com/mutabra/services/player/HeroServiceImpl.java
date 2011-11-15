package com.mutabra.services.player;

import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl extends BaseEntityServiceImpl<Hero> implements HeroService {
	private final Class<? extends Hero> realEntityClass;

	public HeroServiceImpl(final EntityRepository repository) {
		super(repository, Hero.class);
		realEntityClass = repository.create(Hero.class).getClass();
	}

	public Hero create(final Account account) {
		return ReflectionUtils.newInstance(realEntityClass, account);
	}
}
