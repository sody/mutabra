package com.mutabra.security;

import org.scribe.builder.api.VkontakteApi;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class VKontakteServiceImpl extends BaseOAuthServiceImpl implements VKontakteService {
	public VKontakteServiceImpl(final String apiKey, final String apiSecret, final String callbackURL) {
		super(VkontakteApi.class, apiKey, apiSecret, callbackURL);
	}

	@Override
	public String getAuthorizationURL() {
		// request token are not supported
		return service().getAuthorizationUrl(null);
	}
}
