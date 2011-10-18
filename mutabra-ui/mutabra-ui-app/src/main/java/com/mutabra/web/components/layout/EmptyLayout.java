/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.components.layout;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BindingSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Import(stack = "mutabra",
		stylesheet = {
				"context:css/flag.css",
				"context:css/main-theme.css"
		})
@SupportsInformalParameters
public class EmptyLayout extends AbstractComponent {
	private static final String PAGE_TITLE_PROPERTY = "title";

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Inject
	private BindingSource bindingSource;

	@Inject
	private JavaScriptSupport support;

	Binding defaultTitle() {
		return bindingSource.newBinding("Page title", getResources().getContainerResources(), BindingConstants.PROP, PAGE_TITLE_PROPERTY);
	}

	@AfterRender
	void afterRender() {
		support.addInitializerCall("jquery_ui", "");
	}
}
