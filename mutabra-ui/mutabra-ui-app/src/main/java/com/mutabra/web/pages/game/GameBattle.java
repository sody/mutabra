package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.*;
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

    @Override
    public String getSubtitle() {
        return format("subtitle", label("round"), battle.getRound());
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        battle = accountContext.getBattle();
        if (battle == null) {
            return GameHome.class;
        }
        return null;
    }

    @OnEvent("skipTurn")
    Object skipTurn(final BattleHero hero) {
        battleService.skip(battle, hero);
        return null;
    }

    @OnEvent("cast")
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

    @OnEvent("cast")
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
