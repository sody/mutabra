package com.mutabra.services.game;

import com.mutabra.domain.common.Level;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityServiceImpl;
import com.mutabra.services.CodedEntityService;
import org.greatage.domain.EntityRepository;
import org.greatage.util.ReflectionUtils;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl extends BaseEntityServiceImpl<Hero> implements HeroService {
	private final Class<? extends Hero> realEntityClass;
	private final CodedEntityService<Level> levelService;

	public HeroServiceImpl(final EntityRepository repository, final CodedEntityService<Level> levelService) {
		super(repository, Hero.class);
		this.levelService = levelService;

		realEntityClass = repository.create(Hero.class).getClass();
	}

	public Hero create(final Account account) {
		final Hero hero = ReflectionUtils.newInstance(realEntityClass, account);
		hero.setName(account.getName());
		hero.setLevel(levelService.get("newbie"));
		hero.setDefence(10);
		return hero;
	}
}
