/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.components;

import com.noname.web.pages.Index;
import com.noname.web.pages.security.Login;
import com.noname.web.services.GameUser;
import ga.security.context.UserContext;
import ga.tapestry.commonlib.base.components.AbstractComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	public UserContext getUserContext() {
		return userContext;
	}

	public boolean isAuthorized() {
		return userContext.getUser() != null;
	}

	public String getTitle() {
		return title;
	}

	Object onLogin() {
		return Login.class;
	}

	Object onLogout() {
		userContext.setUser(null);
		return Index.class;
	}

}
