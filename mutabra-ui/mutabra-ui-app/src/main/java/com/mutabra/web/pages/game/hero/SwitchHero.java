package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;
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
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class SwitchHero extends AbstractPage {

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @Inject
    private HeroService heroService;

    @Inject
    private AccountContext accountContext;

    @Property
    private List<Hero> source;

    @Property
    private Hero hero;

    @Property
    private Hero row;

    @Property
    private int index;

    @OnEvent(EventConstants.ACTIVATE)
    Object activate() {
        final Account account = accountContext.getAccount();
        hero = accountContext.getHero();
        source = heroService.query().filter("account =", account).asList();
        if (source.isEmpty()) {
            return CreateHero.class;
        }
        return null;
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object enter() {
        // enter the game with just created character
        final Account account = accountContext.getAccount();
        account.setHero(hero);
        accountService.save(account);

        return back();
    }

    @OnEvent
    Object cancel() {
        return back();
    }

    private Object back() {
        return GameHome.class;
    }
}
