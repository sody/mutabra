package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Race;
import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.game.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
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
public class CreateHero extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@InjectService("raceService")
	private CodedEntityService<Race> raceService;

	@Inject
	private HeroService heroService;

	@Property
	private Hero value;

	@Property
	private Race row;

	@Property
	private int index;

	@Inject
	private AccountContext accountContext;

	@Cached
	public List<Race> getRaces() {
		return raceService.query().list();
	}

	@OnEvent(EventConstants.ACTIVATE)
	void activate() {
		value = heroService.create(accountContext.getAccount());
	}

	@OnEvent(value = EventConstants.SUCCESS)
	Object createHero() {
		heroService.saveOrUpdate(value);

		// enter the game with just created character
		final Account account = accountContext.getAccount();
		account.setHero(value);
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
