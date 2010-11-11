package com.noname.services.player;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.services.BaseEntityServiceImpl;
import com.noname.services.common.LevelService;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 */
public class HeroServiceImpl
		extends BaseEntityServiceImpl<Hero, HeroQuery>
		implements HeroService {

	private final LevelService levelService;

	public HeroServiceImpl(final EntityRepository repository, final LevelService levelService) {
		super(repository, Hero.class, HeroQuery.class);
		this.levelService = levelService;
	}

	@Override
	public Hero createHero(final Account account) {
		final Hero hero = create();
		hero.setAccount(account);
		hero.setLevel(levelService.getDefaultLevel());
		hero.setAttack(10);
		hero.setDefence(10);
		return hero;
	}
}
