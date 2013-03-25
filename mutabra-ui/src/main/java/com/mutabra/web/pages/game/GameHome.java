package com.mutabra.web.pages.game;

import com.mutabra.domain.battle.Battle;
import com.mutabra.services.battle.BattleService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.hero.CreateHero;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class GameHome extends AbstractPage {

    @Inject
    private BattleService battleService;

    @Inject
    private AccountContext accountContext;

    @Property
    private Battle battle;

    public List<Battle> getBattles() {
        return battleService.findBattles();
    }

    public boolean isCanApplyBattle() {
        return accountContext.getBattle() == null;
    }

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        if (accountContext.getAccount().getHero() == null) {
            //todo: add more complex rules
            return CreateHero.class;
        }
        if (accountContext.getBattle() != null && accountContext.getBattle().isActive()) {
            return GameBattle.class;
        }
        return null;
    }

    @OnEvent("create")
    Object create() {
        battleService.create(accountContext.getHero());
        return null;
    }

    @OnEvent("apply")
    Object apply(final Battle battle) {
        if (!battle.isActive()) {
            battleService.apply(battle, accountContext.getHero());
        }
        return null;
    }
}
