package com.mutabra.web.services;

import org.apache.tapestry5.Link;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LinkManager {

	Link createPageEventLink(Class<?> pageClass, String eventType, Object... context);
}
