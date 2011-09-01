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
		return getMessages().get("title");
	}
}