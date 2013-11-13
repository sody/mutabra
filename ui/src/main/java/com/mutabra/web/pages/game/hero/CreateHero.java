/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class CreateHero extends AbstractPage {

    @Inject
    private HeroService heroService;

    @Property
    private Race race;

    @Property
    private Face face;

    @Property
    private String name;

    @Inject
    private AccountContext accountContext;

    @OnEvent(value = EventConstants.SUCCESS)
    Object createHero() {
        final Account account = accountContext.getAccount();
        final Hero hero = heroService.create(account, race, face, name);

        // enter the game with just created character
        heroService.enter(account, hero);

        return GameHome.class;
    }
}
