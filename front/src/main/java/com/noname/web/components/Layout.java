/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.components;

import com.noname.web.pages.Index;
import com.noname.web.services.security.GameUser;
import com.noname.web.services.security.SecurityService;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.context.UserContext;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 */
@Import(stylesheet = "main.css")
public class Layout extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.MESSAGE)
	private String title;

	Binding defaultTitle() {
		return newBinding("default title", getResources().getContainerResources(), BindingConstants.PROP, "title");
	}

	@Inject
	private UserContext<GameUser> userContext;

	@Inject
	private SecurityService securityService;

	public String getUser() {
		return userContext.getUser().getName();
	}

	public boolean isAuthenticated() {
		return securityService.isAuthenticated();
	}

	public String getTitle() {
		return title;
	}

	Object onSignOut() {
		securityService.signOut();
		return Index.class;
	}
}
