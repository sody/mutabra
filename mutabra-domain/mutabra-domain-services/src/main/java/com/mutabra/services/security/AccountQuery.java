package com.mutabra.services.security;

import com.mutabra.domain.security.Account;
import com.mutabra.services.BaseEntityQuery;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountQuery extends BaseEntityQuery<Account, AccountQuery> {
	private String email;
	private String password;
	private String token;

	private String facebook;
	private String twitter;
	private String google;
	private String pendingEmail;
	private String pendingToken;

	public AccountQuery(final EntityRepository repository) {
		super(repository, Account.class);
	}

	public AccountQuery withEmail(final String email) {
		this.email = email;
		return query();
	}

	public AccountQuery withPendingEmail(final String email) {
		this.pendingEmail = email;
		return query();
	}

	public AccountQuery withPassword(final String password) {
		this.password = password;
		return query();
	}

	public AccountQuery withToken(final String token) {
		this.token = token;
		return query();
	}

	public AccountQuery withPendingToken(final String token) {
		this.pendingToken = token;
		return query();
	}

	public AccountQuery withFacebook(final String facebook) {
		this.facebook = facebook;
		return query();
	}

	public AccountQuery withTwitter(final String twitter) {
		this.twitter = twitter;
		return query();
	}

	public AccountQuery withGoogle(final String google) {
		this.google = google;
		return query();
	}

	public String getEmail() {
		return email;
	}

	public String getPendingEmail() {
		return pendingEmail;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public String getPendingToken() {
		return pendingToken;
	}

	public String getFacebook() {
		return facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public String getGoogle() {
		return google;
	}
}
