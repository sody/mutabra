package com.mutabra.services.game;

import com.mutabra.domain.common.Levels;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.Repository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HeroServiceImpl extends BaseEntityServiceImpl<Hero> implements HeroService {
    public HeroServiceImpl(final Repository repository) {
        super(repository, Hero.class);
    }

    public void save(final Hero hero, final Account account, final Race race) {
        hero.setAccount(account);

        //TODO: should be retrieved from level
        hero.getLevel().setCode(Levels.NEWBIE);
        hero.getLevel().setRating(0);

        hero.setHealth(race.getHealth());
        hero.setMentalPower(race.getMentalPower());
        hero.getCards().addAll(race.getCards());

        save(hero);
    }
}
