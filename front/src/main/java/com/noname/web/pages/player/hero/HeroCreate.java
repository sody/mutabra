package com.noname.web.pages.player.hero;

import com.noname.domain.player.Hero;
import com.noname.services.player.HeroService;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.security.GameUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Secured;
import org.greatage.security.context.UserContext;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Secured
public class HeroCreate extends AbstractPage {

	@Inject
	private HeroService heroService;

	@Inject
	private UserContext<GameUser> userContext;

	@InjectPage
	private HeroSelect heroSelectPage;

	@Component
	private Form createForm;

	private Hero record;

	public Hero getRecord() {
		return record;
	}

	public void setRecord(Hero record) {
		this.record = record;
	}

	void onActivate() {
		record = heroService.create();
	}

	Object onCreate() {
		if (createForm.isValid()) {
			final Hero hero = getRecord();
			hero.setAccount(userContext.getUser().getAccount());
			heroService.save(hero);
			return onCancel();
		}
		return null;
	}

	Object onCancel() {
		return heroSelectPage;
	}
}
