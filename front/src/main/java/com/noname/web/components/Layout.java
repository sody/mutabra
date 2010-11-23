/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.components;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.security.SecurityContext;
import org.greatage.tapestry.commonlib.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(stylesheet = "main.css")
public class Layout extends AbstractComponent {

	@Parameter(defaultPrefix = BindingConstants.MESSAGE)
	private String title;

	Binding defaultTitle() {
		return newBinding("default title", getResources().getContainerResources(), BindingConstants.PROP, "title");
	}

	@Inject
	private SecurityContext securityContext;

	public String getUser() {
		return securityContext.getAuthentication().getName();
	}

	public boolean isAuthenticated() {
		return securityContext.getAuthentication() != null;
	}

	public String getTitle() {
		return title;
	}
}
