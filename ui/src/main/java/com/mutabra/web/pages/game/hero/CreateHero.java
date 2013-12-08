/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearancePart;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.BaseEntityDataSource;
import com.mutabra.web.internal.NumberDataSource;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.internal.grid.CollectionGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

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

    @Property
    private Hero hero;

    @Property
    private HeroAppearancePart facePart;

    private HeroAppearancePart currentPart = HeroAppearancePart.FACE;

    @InjectComponent
    private Zone faceMenu;

    @InjectComponent
    private Zone faceSelect;

    @Inject
    private AjaxResponseRenderer renderer;

    @InjectService("raceService")
    private CodedEntityService<Race> raceService;

    @Inject
    private HeroService heroService;

    @Inject
    private AccountContext accountContext;

    public Set<HeroAppearancePart> getFaceParts() {
        return EnumSet.of(
                HeroAppearancePart.RACE,
                HeroAppearancePart.SEX,
                HeroAppearancePart.FACE,
//                HeroAppearancePart.EARS,
                HeroAppearancePart.EYES,
//                HeroAppearancePart.EYEBROWS,
//                HeroAppearancePart.NOSE,
                HeroAppearancePart.MOUTH,
                HeroAppearancePart.HAIR,
//                HeroAppearancePart.FACIAL_HAIR,
                HeroAppearancePart.NAME);
    }

    public String getFacePartLabel() {
        return translate(facePart);
    }

    public String getFacePartCssClass() {
        return currentPart == facePart ? " active" : "";
    }

    public Object getFacePartContent() {
        return getResources().findBlock(currentPart.getCode());
    }

    public GridDataSource getFacePartDataSource() {
        return new NumberDataSource(currentPart.getCount());
    }

    @Cached
    public GridDataSource getRaceDataSource() {
        return new BaseEntityDataSource<>(raceService.query(), Race.class);
    }

    @Cached
    public GridDataSource getSexDataSource() {
        return new CollectionGridDataSource(Arrays.asList(Sex.values()));
    }

    @OnEvent(EventConstants.ACTIVATE)
    void activate() {
        hero = new Hero();
        heroService.randomize(hero);
    }

    @OnEvent(component = "menuLink")
    void changePart(final HeroAppearancePart part) {
        currentPart = part;

        renderer.addRender(faceMenu)
                .addRender(faceSelect);
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object createHero() {
        final Account account = accountContext.getAccount();
        final Race race = raceService.get(hero.getAppearance().getRace());
        heroService.create(account, hero, race);

        // enter the game with just created character
        heroService.enter(account, hero);

        return GameHome.class;
    }
}
