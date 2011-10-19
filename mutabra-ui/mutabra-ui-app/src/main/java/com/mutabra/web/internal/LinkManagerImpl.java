package com.mutabra.web.internal;

import com.mutabra.web.services.LinkManager;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.services.ComponentClassResolver;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LinkManagerImpl implements LinkManager {
	private final RequestPageCache requestPageCache;
	private final ComponentClassResolver componentClassResolver;

	public LinkManagerImpl(final RequestPageCache requestPageCache,
						   final ComponentClassResolver componentClassResolver) {
		this.requestPageCache = requestPageCache;
		this.componentClassResolver = componentClassResolver;
	}

	public Link createPageEventLink(final Class<?> pageClass, final String eventType, final Object... context) {
		final String pageName = componentClassResolver.resolvePageClassNameToPageName(pageClass.getName());
		final Page page = requestPageCache.get(pageName);
		return page.getRootElement().createEventLink(eventType, context);
	}
}
