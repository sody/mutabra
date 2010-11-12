package com.noname.web.pages.security;

import com.noname.web.base.pages.AbstractPage;
import com.noname.web.pages.player.hero.HeroCreate;
import com.noname.web.services.security.SecurityService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Activate extends AbstractPage {

	@Property
	private String resultMessage;

	@Inject
	private SecurityService securityService;

	Object onActivate(final String email, final String token) {
		try {
			securityService.activate(email, token);
			return HeroCreate.class;
		} catch (RuntimeException e) {
			resultMessage = e.getMessage();
		}
		return null;
	}
}
