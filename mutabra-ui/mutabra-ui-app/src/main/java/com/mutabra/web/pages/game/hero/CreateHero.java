package com.mutabra.web.pages.game.hero;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Level;
import com.mutabra.domain.common.Race;
import com.mutabra.domain.player.Hero;
import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.CodedEntityService;
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

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Allow(Authorities.ROLE_USER)
public class CreateHero extends AbstractPage {

	@InjectService("accountService")
	private BaseEntityService<Account> accountService;

	@Inject
	private HeroService heroService;

	@InjectService("levelService")
	private CodedEntityService<Level> levelService;

	@InjectService("raceService")
	private CodedEntityService<Race> raceService;

	@InjectService("faceService")
	private CodedEntityService<Face> faceService;

	@Property
	private Hero value;

	@Inject
	private AccountContext accountContext;

	@OnEvent(EventConstants.ACTIVATE)
	void activate() {
		value = heroService.create(accountContext.getAccount());
	}

	@OnEvent(value = EventConstants.SUCCESS)
	Object createHero() {
		//todo: move this to service layer
		final Level level = levelService.get("newbie");
		value.setLevel(level);

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
