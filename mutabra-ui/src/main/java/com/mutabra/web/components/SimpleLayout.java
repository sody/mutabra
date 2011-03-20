/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;

/**
 * @author Ivan Khalopik
 */
@Import(stylesheet = "main.css")
public class SimpleLayout {

	@Parameter(defaultPrefix = BindingConstants.MESSAGE)
	private String title;

	public String getTitle() {
		return title;
	}
}
