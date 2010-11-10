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

	private LevelService levelService;

	public HeroServiceImpl(final EntityRepository repository) {
		super(repository, Hero.class, HeroQuery.class);
	}

	public void setLevelService(final LevelService levelService) {
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
