/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.pages.game.hero.SwitchHero;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
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
public class GameHome extends AbstractPage {
    private static final int EXPIRATION_TIME = 60000;

    @Inject
    private BattleService battleService;

    @Inject
    private AccountContext accountContext;

    @Property
    private Battle battle;

    @Property
    private boolean canApplyBattle;

    public List<Battle> getBattles() {
        return battleService.findBattles(EXPIRATION_TIME);
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

    @OnEvent("create")
    void create() {
        if (canApplyBattle) {
            battleService.create(accountContext.getHero());
        }
    }

    @OnEvent("cancel")
    void cancel() {
        if (!canApplyBattle) {
            battleService.delete(accountContext.getBattle());
        }
    }

    @OnEvent("apply")
    void apply(final Battle battle) {
        if (canApplyBattle && !battle.isActive()) {
            battleService.apply(battle, accountContext.getHero());
        }
    }
}
