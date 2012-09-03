package com.mutabra.web.internal.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.HttpServletRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityRequestFilter implements HttpServletRequestFilter {
	private final Logger log = LoggerFactory.getLogger(SecurityRequestFilter.class);

	private final ServletContext servletContext;
	private final WebSecurityManager securityManager;

	public SecurityRequestFilter(final WebEnvironment environment) {
		this.servletContext = environment.getServletContext();
		this.securityManager = environment.getWebSecurityManager();
	}

	protected ServletContext getServletContext() {
		return servletContext;
	}

	protected WebSecurityManager getSecurityManager() {
		return securityManager;
	}

	protected boolean isHttpSessions() {
		return getSecurityManager().isHttpSessionMode();
	}

	public boolean service(final HttpServletRequest request,
						   final HttpServletResponse response,
						   final HttpServletRequestHandler handler) throws IOException {
		try {
			final HttpServletRequest preparedRequest = prepareServletRequest(request);
			final HttpServletResponse preparedResponse = prepareServletResponse(preparedRequest, response);

			final Subject subject = createSubject(preparedRequest, preparedResponse);

			return subject.execute(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					updateSessionLastAccessTime();
					return handler.service(preparedRequest, preparedResponse);
				}
			});
		} catch (ExecutionException ex) {
			rethrowException(ex.getCause());
		} catch (Throwable throwable) {
			rethrowException(throwable);
		}

		// never happens
		return false;
	}

	private HttpServletRequest prepareServletRequest(final HttpServletRequest request) {
		return new ShiroHttpServletRequest(request, getServletContext(), isHttpSessions());
	}

	private HttpServletResponse prepareServletResponse(final HttpServletRequest request,
													   final HttpServletResponse response) {
		return !isHttpSessions() && request instanceof ShiroHttpServletRequest ?
				new ShiroHttpServletResponse(response, getServletContext(), (ShiroHttpServletRequest) request) :
				response;
	}

	private WebSubject createSubject(final HttpServletRequest request, final HttpServletResponse response) {
		return new WebSubject.Builder(getSecurityManager(), request, response).buildWebSubject();
	}

	private void rethrowException(final Throwable throwable) throws IOException {
		if (throwable != null) {
			if (throwable instanceof IOException) {
				throw (IOException) throwable;
			}
			if (throwable instanceof RuntimeException) {
				throw (RuntimeException) throwable;
			}
			//otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
			throw new RuntimeException("Filtered request failed.", throwable);
		}
	}

	private void updateSessionLastAccessTime() {
		if (!isHttpSessions()) { //'native' sessions
			final Subject subject = SecurityUtils.getSubject();
			//Subject should never _ever_ be null, but just in case:
			if (subject != null) {
				final Session session = subject.getSession(false);
				if (session != null) {
					try {
						session.touch();
					} catch (Throwable t) {
						log.error("session.touch() method invocation has failed.  Unable to update" +
								"the corresponding session's last access time based on the incoming request.", t);
					}
				}
			}
		}
	}
}
