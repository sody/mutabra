/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
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
@Import(stylesheet = {
		"context:css/reset.css",
		"context:css/base.css",
		"context:css/fonts.css",
		"context:css/layout.css",
		"context:css/main.css"
})
@SupportsInformalParameters
public class Layout extends AbstractComponent {
	private static final String PAGE_TITLE_PROPERTY = "title";

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Inject
	private BindingSource bindingSource;

	Binding defaultTitle() {
		return bindingSource.newBinding("Page title", getResources().getContainerResources(), BindingConstants.PROP, PAGE_TITLE_PROPERTY);
	}

	public Block getRightBody() {
		return getResources().getBlockParameter("right");
	}

	public boolean isLoggedIn() {
		return true;
	}

	public String getUser() {
		return "user";
	}
}
