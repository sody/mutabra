/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BindingSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(stack = "mutabra",
		stylesheet = {
				"context:css/flag.css",
				"context:css/main.css"
		})
@SupportsInformalParameters
public class Layout extends AbstractComponent {
	private static final String PAGE_TITLE_PROPERTY = "title";

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String[] buttons;

	@Property
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

	@Inject
	private BindingSource bindingSource;

	Binding defaultTitle() {
		return bindingSource.newBinding("Page title", getResources().getContainerResources(), BindingConstants.PROP, PAGE_TITLE_PROPERTY);
	}

	public boolean isLoggedIn() {
		return true;
	}

	public String getUser() {
		return "Account";
	}
}
