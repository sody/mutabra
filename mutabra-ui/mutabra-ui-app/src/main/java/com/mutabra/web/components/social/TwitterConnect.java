package com.mutabra.web.components.social;

import com.mutabra.security.OAuth;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.base.AbstractComponentEventLink;
import org.apache.tapestry5.internal.util.CaptureResultCallback;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterConnect extends AbstractComponentEventLink {
	private static final String CONNECT_EVENT = "connect";
	private static final String CONNECTED_EVENT = "connected";

	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String scope;

	@Inject
	private ComponentResources resources;

	@InjectService("twitterService")
	private OAuth twitterService;

	@Cached
	protected String getCallbackUri() {
		return resources.createEventLink(CONNECTED_EVENT).toAbsoluteURI();
	}

	@Override
	protected Link createLink(final Object[] eventContext) {
		return resources.createEventLink(CONNECT_EVENT);
	}

	@OnEvent(CONNECT_EVENT)
	URL connect() throws MalformedURLException {
		return new URL(twitterService.getAuthorizationUrl(getCallbackUri(), scope));
	}

	@OnEvent(CONNECTED_EVENT)
	Object connected(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier,
			@RequestParameter(value = "denied", allowBlank = true) final String denied) {

		final CaptureResultCallback<Object> callback = new CaptureResultCallback<Object>();
		if (verifier != null) {
			try {
				final OAuth.Session session = twitterService.connect(token, verifier, getCallbackUri(), scope);
				final Object[] context = {session};
				final boolean handled = resources.triggerEvent(EventConstants.SUCCESS, context, callback);

				if (handled) {
					return callback.getResult();
				}
				return null;
			} catch (Exception e) {
				//todo: log error
			}
		}

		final Object[] context = {denied};
		final boolean handled = resources.triggerEvent(EventConstants.FAILURE, context, callback);

		if (handled) {
			return callback.getResult();
		}
		return null;
	}
}
