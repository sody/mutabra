package com.mutabra.web.internal;

import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityExceptionHandler implements RequestExceptionHandler {
	private final RequestExceptionHandler delegate;
	private final ResponseRenderer renderer;
	private final String loginPage;

	public SecurityExceptionHandler(final RequestExceptionHandler delegate,
									final ResponseRenderer renderer,
									final String loginPage) {

		this.delegate = delegate;
		this.renderer = renderer;
		this.loginPage = loginPage;
	}

	@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
	public void handleRequestException(final Throwable exception) throws IOException {
		final Throwable rootException = getRootCause(exception);
		if (rootException instanceof SecurityException) {
			//todo: log it
			renderer.renderPageMarkupResponse(loginPage);
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
