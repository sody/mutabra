package com.noname.web.pages.player.hero;

import com.noname.domain.player.Hero;
import com.noname.services.player.HeroService;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class HeroSelect extends AbstractPage {

	@Inject
	private HeroService heroService;

	@InjectPage
	private HeroCreate heroCreatePage;

	private List<Hero> heroes;

	private Hero row;

	public Hero getRow() {
		return row;
	}

	public void setRow(Hero row) {
		this.row = row;
	}

	public List<Hero> getHeroes() {
		return heroes;
	}

	void setupRender() {
		heroes = heroService.getEntities();
	}

	Object onCreate() {
		return heroCreatePage;
	}
}
