package com.mutabra.web.pages.game;

import com.mutabra.domain.game.Hero;
import com.mutabra.services.battle.BattleService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.hero.CreateHero;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class GameHome extends AbstractPage {

    @Inject
    private HeroService heroService;

    @Inject
    private BattleService battleService;

    @Inject
    private AccountContext accountContext;

    @Property
    private Hero row;

    private Hero hero;

    public List<Hero> getPlayers() {
        final Date timeout = new Date(System.currentTimeMillis() - 50000);
        return heroService.query()
                .filter("lastActive >", timeout)
                .limit(20)
                .asList();
    }

    public boolean isCanCreateBattle() {
        return row.equals(hero);
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        if (accountContext.getHero() == null) {
            //todo: add more complex rules
            return CreateHero.class;
        }
        if (accountContext.getBattle() != null) {
            return GameBattle.class;
        }
        hero = accountContext.getHero();
        return null;
    }

    @OnEvent("createBattle")
    Object createBattle(final Hero target) {
        if (battleService.query().filter("heroes.id in", Arrays.asList(hero.getId(), target.getId())).countAll() == 0) {
            battleService.create(hero, target);
        }
        return null;
    }
}
