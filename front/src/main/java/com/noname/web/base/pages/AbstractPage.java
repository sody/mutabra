/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.base.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractPage {

	@Inject
	private Messages messages;

	@Inject
	private Locale locale;

	public Messages getMessages() {
		return messages;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getTitle() {
		//todo: make more complex title obtaining(with prefix and suffix)
		return getMessages().get("title");
	}
}