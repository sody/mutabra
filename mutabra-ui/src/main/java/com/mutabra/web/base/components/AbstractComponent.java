package com.mutabra.web.base.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AbstractComponent {

	@Inject
	private ComponentResources resources;

	@Inject
	private Messages messages;

	@Inject
	private Locale locale;

	public ComponentResources getResources() {
		return resources;
	}

	public Messages getMessages() {
		return messages;
	}

	public Locale getLocale() {
		return locale;
	}
}
