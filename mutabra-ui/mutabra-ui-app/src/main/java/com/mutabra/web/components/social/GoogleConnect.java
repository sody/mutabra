package com.mutabra.web.components.social;

import com.mutabra.security.OAuth;
import com.mutabra.web.base.components.AbstractOAuthConnect;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GoogleConnect extends AbstractOAuthConnect {

	@InjectService("googleService")
	private OAuth googleService;

	@Override
	protected OAuth getOAuth() {
		return googleService;
	}

	@OnEvent(CONNECTED_EVENT)
	Object connected(
			@RequestParameter(value = "oauth_token", allowBlank = true) String token,
			@RequestParameter(value = "oauth_verifier", allowBlank = true) final String verifier) {

		final String info = new DescriptionBuilder("GOOGLE TOKEN")
				.append("token", token)
				.append("verifier", verifier)
				.toString();
		System.out.println(info);

		return doConnected(token, verifier, null);
	}
}
