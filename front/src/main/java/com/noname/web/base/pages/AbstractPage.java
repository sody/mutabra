/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.noname.web.base.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 */
public abstract class AbstractPage {

	@Inject
	private Request request;

	@Inject
	private Response response;

	@Inject
	private Messages messages;

	@Inject
	private Locale locale;

	protected Request getRequest() {
		return request;
	}

	public Response getResponse() {
		return response;
	}

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