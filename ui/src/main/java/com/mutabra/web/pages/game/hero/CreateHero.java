/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import static com.mutabra.web.internal.annotations.MainMenuItem.HERO;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
@MainMenu(HERO)
@Import(library = "context:/mutabra/js/face-generator.js")
public class CreateHero extends AbstractPage {

    @Inject
    private HeroService heroService;

    @Property
    private Race race;

    @Property
    private Face face;

    @Property
    private String name;

    @Property
    private Hero hero;

    @Inject
    private AccountContext accountContext;

    @OnEvent(EventConstants.ACTIVATE)
    void activate() {
        hero = new Hero();
        hero.getAppearance().setName("Hermes");
        hero.getAppearance().setRace("plunger");
        hero.getAppearance().setSex(Sex.MALE);
        hero.getAppearance().setEars(1);
        hero.getAppearance().setFace(1);
        hero.getAppearance().setEyes(1);
        hero.getAppearance().setEyebrows(1);
        hero.getAppearance().setNose(1);
        hero.getAppearance().setMouth(1);
        hero.getAppearance().setHair(1);
        hero.getAppearance().setFacialHair(1);
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object createHero() {
        final Account account = accountContext.getAccount();
        heroService.create(account, hero, race);

        // enter the game with just created character
        heroService.enter(account, hero);

        return GameHome.class;
    }
}
