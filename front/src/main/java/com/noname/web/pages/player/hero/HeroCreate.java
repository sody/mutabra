package com.noname.web.pages.player.hero;

import com.noname.domain.player.Hero;
import com.noname.domain.security.Account;
import com.noname.services.player.HeroService;
import com.noname.web.base.pages.AbstractPage;
import com.noname.web.services.GameUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Secured;
import org.greatage.security.context.UserContext;

/**
 * @author Ivan Khalopik
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
		final Account account = userContext.getUser().getAccount();
		record = heroService.createHero(account);
	}

	Object onCreate() {
		if (createForm.isValid()) {
			heroService.save(getRecord());
			return onCancel();
		}
		return null;
	}

	Object onCancel() {
		return heroSelectPage;
	}
}
