package com.mutabra.web.pages.game.hero;

import com.googlecode.objectify.Key;
import com.mutabra.domain.Keys;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.player.HeroImpl;
import com.mutabra.domain.security.Account;
import com.mutabra.domain.security.AccountImpl;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.player.HeroService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.Authorities;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.security.annotations.Allow;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class SwitchHero extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@Inject
	private HeroService heroService;

	@Inject
	private AccountContext accountContext;

	@Property
	private Set<Hero> source;

	@Property
	private Hero value;

	@Property
	private Hero row;

	@Property
	private int index;

	@OnEvent(EventConstants.ACTIVATE)
	Object activate() {
		value = accountContext.getHero();
		source = accountContext.getAccount().getHeroes();
		if (source.isEmpty()) {
			return CreateHero.class;
		}
		return null;
	}

	@OnEvent("enter")
	Object enter(final long number) {
		// enter the game with just created character
		final Account account = accountContext.getAccount();

		//todo: appengine dependent part, remove it
		final Key<Hero> heroKey = new Key<Hero>(new Key<Account>(AccountImpl.class, account.getId()), HeroImpl.class, number);
		value = Keys.getInstance(heroKey);

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
