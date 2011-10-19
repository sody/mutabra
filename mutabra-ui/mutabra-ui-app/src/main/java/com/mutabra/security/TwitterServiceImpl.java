package com.mutabra.security;

import org.scribe.builder.api.TwitterApi;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TwitterServiceImpl extends BaseOAuthServiceImpl implements TwitterService {

	public TwitterServiceImpl(final String consumerKey, final String consumerSecret, final String callbackURL) {
		super(TwitterApi.class, consumerKey, consumerSecret, callbackURL);
	}
}
