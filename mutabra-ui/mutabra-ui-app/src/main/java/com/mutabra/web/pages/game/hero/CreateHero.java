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

	@OnEvent(value = EventConstants.SUCCESS)
	Object createHero() {
		final Account account = accountContext.getAccount();

		//todo: remove this with form implementation
		value = heroService.create(account);
		final Level level = levelService.get("newbie");
		final Race race = raceService.get("orc");
		final Face face = faceService.get("f2");
		value.setLevel(level);
		value.setRace(race);
		value.setFace(face);

		heroService.saveOrUpdate(value);
		// enter the game with just created character
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
