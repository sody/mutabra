/*
 * Copyright 2000 - 2009 Ivan Khalopik. All Rights Reserved.
 */

package com.mutabra.web.base.pages;

import com.mutabra.web.base.components.AbstractComponent;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbstractPage extends AbstractComponent {

	public String getTitle() {
		//todo: make more complex title obtaining(with prefix and suffix)
		if (getMessages().contains("title")) {
			return getMessages().get("title");
		}
		final String key = "page." + getResources().getPageName().toLowerCase().replaceAll("/","-") + ".title";
		return getMessages().get(key);
	}

	public String getSubtitle() {
		return null;
	}
}