/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.*;
import com.mutabra.services.battle.BattleField;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class GameBattle extends AbstractPage {

    @Inject
    private AccountContext accountContext;

    @Inject
    private BattleService battleService;

    @Property
    private Battle battle;

    @Property
    private BattleField field;

    @Property
    private BattleField.Point point;

    @Property
    private BattleHero hero;

    @Property
    private BattleCard card;

    @Property
    private BattleCreature creature;

    @Property
    private BattleAbility ability;

    @Override
    public String getHeaderNote() {
        return format("subtitle", message("label.round"), battle.getRound());
    }

    public boolean isEnemy() {
        return !field.getSelf().equals(hero);
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        battle = accountContext.getBattle();
        if (battle == null || !battle.isActive()) {
            return GameHome.class;
        }

        for (BattleHero battleHero : battle.getHeroes()) {
            if (battleHero.getId().equals(accountContext.getAccount().getHero().getId())) {
                field = BattleField.create(battle, battleHero);
            }
        }
        return null;
    }

    @OnEvent("skip")
    Object skip(final BattleHero hero) {
        battleService.skip(battle, hero);
        return null;
    }

    @OnEvent(value = "cast", component = "heroHand")
    Object cast(final BattleCard card,
                final @RequestParameter(value = "x") int x,
                final @RequestParameter(value = "y") int y,
                final @RequestParameter(value = "side") BattleSide side) {
        if (!card.getHero().isReady()) {
            final BattleTarget target = new BattleTarget();
            target.setPosition(new BattlePosition(x, y));
            target.setSide(side);
            battleService.cast(battle, card, target);
        }
        return null;
    }

    @OnEvent(value = "cast", component = "creatureHand")
    Object cast(final BattleAbility ability,
                final @RequestParameter(value = "x") int x,
                final @RequestParameter(value = "y") int y,
                final @RequestParameter(value = "side") BattleSide side) {
        if (!ability.getCreature().isReady()) {
            final BattleTarget target = new BattleTarget();
            target.setPosition(new BattlePosition(x, y));
            target.setSide(side);
            battleService.cast(battle, ability, target);
        }
        return null;
    }
}
