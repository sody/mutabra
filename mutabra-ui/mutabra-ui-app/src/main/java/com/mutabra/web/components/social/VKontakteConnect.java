package com.mutabra.web.components.social;

import com.mutabra.security.OAuth2;
import com.mutabra.web.base.components.AbstractOAuth2Connect;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class VKontakteConnect extends AbstractOAuth2Connect {

	@InjectService("vkontakteService")
	private OAuth2 vkontakteService;

	@Override
	protected OAuth2 getOAuth() {
		return vkontakteService;
	}

	@OnEvent(CONNECTED_EVENT)
	Object connected(
			@RequestParameter(value = "code", allowBlank = true) final String code,
			@RequestParameter(value = "error", allowBlank = true) final String error,
			@RequestParameter(value = "error_reason", allowBlank = true) final String errorReason,
			@RequestParameter(value = "error_description", allowBlank = true) final String errorDescription) {

		final String info = new DescriptionBuilder("VKONTAKTE TOKEN")
				.append("code", code)
				.append("error", error)
				.append("error_reason", errorReason)
				.append("error_description", errorDescription)
				.toString();
		System.out.println(info);

		return doConnected(code, errorDescription);
	}
}
