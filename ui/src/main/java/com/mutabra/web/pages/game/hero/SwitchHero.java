/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.mutabra.web.internal.annotations.MainMenuItem.HERO;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
@MainMenu(HERO)
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
    private Hero activeHero;

    public String getHeroItemCssClass() {
        return hero.equals(activeHero) ? " active" : "";
    }

    public Iterable<HeroAppearancePart> getHeroItemFaceParts() {
        return EnumSet.allOf(HeroAppearancePart.class);
    }

    public String getHeroItemValue() {
        return encode(Hero.class, hero);
    }

    public String getHeroItemRaceLabel() {
        return message(Race.BASENAME + "." + hero.getAppearance().getRace() + "." + Translatable.NAME);
    }

    public String getHeroItemLevelLabel() {
        return message(Level.BASENAME + "." + hero.getLevel().getCode() + "." + Translatable.NAME);
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        final Account account = accountContext.getAccount();
        List<Hero> heroes = heroService.getAll(account);
        if (heroes.isEmpty()) {
            return CreateHero.class;
        }
        source = new ArrayList<>(heroes);
        source.add(null);

        // setup selected hero
        final ObjectId currentHeroId = account.getHero() != null ? account.getHero().getId() : null;
        if (currentHeroId != null) {
            for (Hero accountHero : heroes) {
                if (currentHeroId.equals(accountHero.getId())) {
                    activeHero = accountHero;
                }
            }
        }
        return null;
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object enter() {
        // enter the game with just created character
        final Account account = accountContext.getAccount();
        heroService.enter(account, activeHero);

        return GameHome.class;
    }
}
