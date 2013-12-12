/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game;

import com.mutabra.domain.Translatable;
import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.HeroAppearance;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.pages.game.hero.SwitchHero;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import static com.mutabra.web.internal.annotations.MainMenuItem.HOME;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
@MainMenu(HOME)
@Import(library = "context:/mutabra/js/face-generator.js")
public class GameHome extends AbstractPage {
    private static final int EXPIRATION_TIME = 60000;

    @Inject
    private BattleService battleService;

    @Inject
    private AccountContext accountContext;

    @Property
    private Battle battle;

    @Property
    private Battle activeBattle;

    @Property
    private boolean canApplyBattle;

    public List<Battle> getSource() {
        return battleService.findBattles(EXPIRATION_TIME);
    }

    public BattleHero getHero() {
        return battle.getHeroes().get(0);
    }

    public String getBattleItemValue() {
        return encode(Battle.class, battle);
    }

    public String getHeroRaceLabel() {
        return message(Race.BASENAME + "." + getHero().getAppearance().getRace() + "." + Translatable.NAME);
    }

    public String getHeroLevelLabel() {
        return message(Level.BASENAME + "." + getHero().getLevel().getCode() + "." + Translatable.NAME);
    }

    public HeroAppearance getFakeAppearance() {
        return null;
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        if (accountContext.getAccount().getHero() == null) {
            //todo: add more complex rules
            // not entered the game
            return SwitchHero.class;
        }

        final Battle battle = accountContext.getBattle();
        if (battle != null && battle.isActive()) {
            // already in battle
            return GameBattle.class;
        }

        canApplyBattle = battle == null;
        if (battle != null && battle.isExpired(EXPIRATION_TIME)) {
            // remove expired battle
            battleService.delete(battle);
            canApplyBattle = true;
        }

        return null;
    }

    @OnEvent(component = "create")
    void create() {
        if (canApplyBattle) {
            battleService.create(accountContext.getHero());
        }
    }

    @OnEvent(component = "cancel")
    void cancel() {
        if (!canApplyBattle) {
            battleService.delete(accountContext.getBattle());
        }
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "battleForm")
    void validateBattle() throws ValidationException {
        if (!canApplyBattle || activeBattle == null || activeBattle.isActive()) {
            throw new ValidationException(message("error.wrong-battle"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "battleForm")
    void apply() {
        if (canApplyBattle && !activeBattle.isActive()) {
            battleService.apply(activeBattle, accountContext.getHero());
        }
    }
}
