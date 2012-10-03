package com.mutabra.web.internal.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.Response;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
	private final RequestExceptionHandler delegate;
	private final PageRenderLinkSource linkSource;
	private final Response response;
	private final Class loginPage;

	public SecurityExceptionHandler(final RequestExceptionHandler delegate,
									final PageRenderLinkSource linkSource,
									final Response response,
									final Class loginPage) {
		this.delegate = delegate;
		this.linkSource = linkSource;
		this.response = response;
		this.loginPage = loginPage;
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public void handleRequestException(final Throwable exception) throws IOException {
		final Throwable rootException = getRootCause(exception);
		if (rootException instanceof AuthenticationException || rootException instanceof UnauthenticatedException) {
			//todo: log it
			final Link loginPageLink = linkSource.createPageRenderLink(loginPage);
			response.sendRedirect(loginPageLink);
		} else {
			delegate.handleRequestException(exception);
		}
	}

	private static Throwable getRootCause(Throwable throwable) {
		Throwable cause;
		while ((cause = throwable.getCause()) != null) {
			throwable = cause;
		}
		return throwable;
	}
}
