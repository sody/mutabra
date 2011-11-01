package com.mutabra.web.pages;

import com.mutabra.web.internal.NotFoundException;
import org.apache.tapestry5.corelib.pages.ExceptionReport;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Error extends ExceptionReport {

	@Inject
	private Response response;

	@Override
	public void reportException(final Throwable exception) {
		final Throwable rootCause = getRootCause(exception);
		if (rootCause instanceof NotFoundException) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		super.reportException(exception);
	}

	private static Throwable getRootCause(Throwable throwable) {
		Throwable cause;
		while ((cause = throwable.getCause()) != null) {
			throwable = cause;
		}
		return throwable;
	}
}
