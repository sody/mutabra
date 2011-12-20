package com.mutabra.web.internal;

import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.greatage.security.AccessDeniedException;
import org.greatage.security.Authentication;
import org.greatage.security.AuthenticationException;
import org.greatage.security.AuthorityConstants;
import org.greatage.security.SecurityContext;
import org.greatage.util.CollectionUtils;
import org.greatage.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityFilter implements ComponentRequestFilter {
	private static final List<String> ANONYMOUS_AUTHORITIES = CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS);

	private final SecurityContext securityContext;
	private final MetaDataLocator locator;

	public SecurityFilter(final SecurityContext securityContext, final MetaDataLocator locator) {
		this.locator = locator;
		this.securityContext = securityContext;
	}

	public void handleComponentEvent(final ComponentEventRequestParameters parameters, final ComponentRequestHandler handler) throws IOException {
		//todo: implement this
		handler.handleComponentEvent(parameters);
	}

	public void handlePageRender(final PageRenderRequestParameters parameters, final ComponentRequestHandler handler) throws IOException {
		final String pageName = parameters.getLogicalPageName();
		final String authority = locator.findMeta(Authorities.PAGE_AUTHORITY_META, pageName, String.class);
		if (!StringUtils.isEmpty(authority)) {
			final Authentication authentication = securityContext.getCurrentUser();
			final List<String> authorities = authentication != null ? authentication.getAuthorities() : ANONYMOUS_AUTHORITIES;

			if (!authorities.contains(authority)) {
				if (authentication == null) {
					throw new AuthenticationException("Not authenticated");
				}
				throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
			}
		}

		handler.handlePageRender(parameters);
	}
}
