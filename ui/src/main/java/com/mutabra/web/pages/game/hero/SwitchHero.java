/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class SwitchHero extends AbstractPage {

    @Inject
    private HeroService heroService;

    @Inject
    private AccountContext accountContext;

    @Property
    private List<Hero> source;

    @Property
    private Hero hero;

    @Property
    private Hero row;

    @Property
    private int index;

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        final Account account = accountContext.getAccount();
        source = heroService.getAll(account);
        if (source.isEmpty()) {
            return CreateHero.class;
        }

        // setup selected hero
        final ObjectId currentHeroId = account.getHero() != null ? account.getHero().getId() : null;
        if (currentHeroId != null) {
            for (Hero accountHero : source) {
                if (currentHeroId.equals(accountHero.getId())) {
                    hero = accountHero;
                }
            }
        }
        return null;
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object enter() {
        // enter the game with just created character
        final Account account = accountContext.getAccount();
        heroService.enter(account, hero);

        return back();
    }

    @OnEvent
    Object cancel() {
        return back();
    }

    private Object back() {
        return GameHome.class;
    }
}
