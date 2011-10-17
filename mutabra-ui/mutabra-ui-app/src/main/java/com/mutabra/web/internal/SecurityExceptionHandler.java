package com.mutabra.web.internal;

import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.greatage.security.*;
import org.greatage.util.ReflectionUtils;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
	private final RequestExceptionHandler delegate;
	private final ResponseRenderer renderer;
	private final ComponentClassResolver resolver;
	private final Class loginPage;

	public SecurityExceptionHandler(final RequestExceptionHandler delegate,
									final ResponseRenderer renderer,
									final ComponentClassResolver resolver,
									final Class loginPage) {

		this.delegate = delegate;
		this.renderer = renderer;
		this.resolver = resolver;
		this.loginPage = loginPage;
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public void handleRequestException(final Throwable exception) throws IOException {
		final org.greatage.security.SecurityException securityException = ReflectionUtils.findException(exception, org.greatage.security.SecurityException.class);
		if (securityException != null) {
			final String pageName = resolver.resolvePageClassNameToPageName(loginPage.getName());
			renderer.renderPageMarkupResponse(pageName);
		} else {
			delegate.handleRequestException(exception);
		}
	}
}
