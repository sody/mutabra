/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.common.Sex;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroAppearance;
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
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.internal.grid.CollectionGridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.util.Arrays;
import java.util.Objects;

import static com.mutabra.web.internal.annotations.MainMenuItem.HERO;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
@MainMenu(HERO)
public class CreateHero extends AbstractPage {

    @Property
    private Hero hero;

    @Property
    private Object facePartItem;

    @Property
    private HeroAppearancePart facePart;

    private HeroAppearancePart currentPart = HeroAppearancePart.FACE;

    @Inject
    private Block facePartContent;

    @Inject
    private Block nameContent;

    @InjectService("raceService")
    private CodedEntityService<Race> raceService;

    @Inject
    private HeroService heroService;

    @Inject
    private AccountContext accountContext;

    public Iterable<HeroAppearancePart> getFaceParts() {
        return Arrays.asList(
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

    public String getFacePartContentId() {
        return "paginator-" + facePart.getCode();
    }

    public String getFacePartLabel() {
        return translate(facePart);
    }

    public String getFacePartCssClass() {
        return currentPart == facePart ? " active" : "";
    }

    public Object getFacePartContent() {
        return facePart == HeroAppearancePart.NAME ? nameContent : facePartContent;
    }

    public GridDataSource getFacePartDataSource() {
        if (facePart == HeroAppearancePart.RACE) {
            return new BaseEntityDataSource<>(raceService.query(), Race.class);
        } else if (facePart == HeroAppearancePart.SEX) {
            return new CollectionGridDataSource(Arrays.asList(Sex.values()));
        }

        return new NumberDataSource(facePart.getCount());
    }

    public String getFacePartItemValue() {
        return encode(getFacePartDataSource().getRowType(), facePartItem);
    }

    public String getFacePartItemCssClass() {
        final Object activeValue = getActiveValue(facePart);
        if (facePart == HeroAppearancePart.RACE) {
            // races are compared by code
            return Objects.equals(activeValue, ((Race) facePartItem).getCode()) ? "face active" : "face";
        }
        return Objects.equals(activeValue, facePartItem) ? "face active" : "face";
    }

    @OnEvent(EventConstants.ACTIVATE)
    void activate() {
        hero = new Hero();
        heroService.randomize(hero);
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

    private Object getActiveValue(final HeroAppearancePart facePart) {
        final HeroAppearance appearance = hero.getAppearance();
        switch (facePart) {
            case NAME:
                return appearance.getName();
            case RACE:
                return appearance.getRace();
            case SEX:
                return appearance.getSex();
            case EARS:
                return appearance.getEars();
            case FACE:
                return appearance.getFace();
            case EYES:
                return appearance.getEyes();
            case EYEBROWS:
                return appearance.getEyebrows();
            case NOSE:
                return appearance.getNose();
            case MOUTH:
                return appearance.getMouth();
            case HAIR:
                return appearance.getHair();
            case FACIAL_HAIR:
                return appearance.getFacialHair();
            default:
                return null;
        }
    }
}
