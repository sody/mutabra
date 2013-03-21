package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresUser
@RequiresPermissions("game:play")
public class CreateHero extends AbstractPage {

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @InjectService("raceService")
    private CodedEntityService<Race> raceService;

    @Inject
    private HeroService heroService;

    @Property
    private Hero hero;

    @Property
    private Race race;

    @Property
    private Face face;

    @Inject
    private AccountContext accountContext;

    @OnEvent(EventConstants.ACTIVATE)
    void activate() {
        hero = new Hero();
    }

    @OnEvent(value = EventConstants.SUCCESS)
    Object createHero() {
        final Account account = accountContext.getAccount();

        hero.getAppearance().setFace(face.getCode());
        heroService.save(hero, account, race);

        // enter the game with just created character
        account.setHero(hero);
        accountService.save(account);

        return back();
    }

    @OnEvent
    Object cancel(final String source) {
        return back();
    }

    private Object back() {
        return GameHome.class;
    }
}
