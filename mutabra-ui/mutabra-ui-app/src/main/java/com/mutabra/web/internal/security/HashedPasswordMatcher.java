package com.mutabra.web.internal.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.util.ByteSource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class HashedPasswordMatcher extends SimpleCredentialsMatcher {
	private final HashService hashService;

	private boolean storedCredentialsHexEncoded;

	public HashedPasswordMatcher(final HashService hashService) {
		this.hashService = hashService;
	}

	public boolean isStoredCredentialsHexEncoded() {
		return storedCredentialsHexEncoded;
	}

	public void setStoredCredentialsHexEncoded(final boolean storedCredentialsHexEncoded) {
		this.storedCredentialsHexEncoded = storedCredentialsHexEncoded;
	}

	@Override
	public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
		final Hash tokenHash = getCredentials(token);
		final Hash accountHash = getCredentials(info);

		// it makes possible to have empty passwords
		if (accountHash == null || accountHash.isEmpty()) {
			return tokenHash == null || tokenHash.isEmpty();
		} else {
			if (tokenHash == null || tokenHash.isEmpty()) {
				return false;
			}
		}

		return accountHash.equals(tokenHash);
	}

	@Override
	protected Hash getCredentials(final AuthenticationInfo info) {
		final Object credentials = info != null ? info.getCredentials() : null;
		// it makes possible to have empty passwords
		if (credentials == null) {
			return null;
		}
		// we can retrieve already generated hash from realm
		if (credentials instanceof Hash) {
			return (Hash) credentials;
		}

		byte[] bytes = toBytes(credentials);
		// text credentials should be decoded
		if (credentials instanceof String || credentials instanceof char[]) {
			if (isStoredCredentialsHexEncoded()) {
				bytes = Hex.decode(bytes);
			} else {
				bytes = Base64.decode(bytes);
			}
		}
		// generate hash using hash service
		return hashService.computeHash(new HashRequest.Builder()
				.setSource(ByteSource.Util.bytes(bytes))
				.setSalt(getSalt(info))
				.build());
	}

	@Override
	protected Hash getCredentials(final AuthenticationToken token) {
		final Object credentials = token != null ? token.getCredentials() : null;
		// it makes possible to have empty passwords
		if (credentials == null) {
			return null;
		}
		// generate hash using hash service
		return hashService.computeHash(new HashRequest.Builder()
				.setSource(credentials)
				.build());
	}

	protected Object getSalt(final AuthenticationInfo info) {
		return info instanceof SaltedAuthenticationInfo ?
				((SaltedAuthenticationInfo) info).getCredentialsSalt() :
				null;
	}
}
