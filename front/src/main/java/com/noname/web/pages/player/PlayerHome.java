package com.noname.web.pages.player;

import com.noname.domain.player.Hero;
import com.noname.game.User;
import com.noname.services.security.AuthorityConstants;
import com.noname.services.security.UserService;
import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.annotations.Authority;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Authority(AuthorityConstants.STATUS_PLAYING)
public class PlayerHome extends AbstractPage {

	@Inject
	private UserService userService;

	@Property
	private User row;

	private Hero hero;
	private List<User> users;

	public Hero getHero() {
		return hero;
	}

	public List<User> getUsers() {
		return users;
	}

	void setupRender() {
		hero = getApplicationContext().getHero();
		users = userService.getPlayingUsers();
		System.out.println("");
	}

}
