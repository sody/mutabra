package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredential;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.AccountPendingToken;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 */
public class ConfirmationRealm extends AuthenticatingRealm implements CredentialsMatcher {
    private final BaseEntityService<Account> accountService;

    public ConfirmationRealm(final BaseEntityService<Account> accountService) {
        this.accountService = accountService;

        setAuthenticationTokenClass(Token.class);
        setCredentialsMatcher(this);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        final Account account = getAccount(token);
        if (account == null) {
            throw new UnknownAccountException("Account not found.");
        }

        final AccountPendingToken pendingToken = account.getPendingToken();
        if (pendingToken == null || pendingToken.getToken() == null) {
            throw new AccountException("Account has no pending changes.");
        }

        if (pendingToken.isExpired()) {
            throw new ExpiredCredentialsException(String.format("Submitted credentials for token [%s] has been expired.", token));
        }

        final String[] credentials = {pendingToken.getToken(), pendingToken.getSecondaryToken()};
        return new SimpleAuthenticationInfo(account.getId(), credentials, getName());
    }

    public boolean doCredentialsMatch(final AuthenticationToken token, final AuthenticationInfo info) {
        final String submittedToken = (String) token.getCredentials();
        final String[] accountTokens = (String[]) info.getCredentials();

        // submitted token can not be null
        if (submittedToken == null) {
            return false;
        }

        // retrieve account tokens
        final String mainToken = accountTokens[0];
        final String secondaryToken = accountTokens[1];

        // main token matches
        if (submittedToken.equals(mainToken)) {
            final Account account = getAccount(token);
            final AccountPendingToken pendingToken = account.getPendingToken();
            // pending token is not null
            if (secondaryToken != null) {
                // replace main token with secondary token
                pendingToken.setToken(secondaryToken);
                pendingToken.setSecondaryToken(null);
            } else {
                // get or create email credential
                AccountCredential emailCredential = account.getCredentials(AccountCredentialType.EMAIL);
                if (emailCredential == null) {
                    emailCredential = new AccountCredential();
                    emailCredential.setType(AccountCredentialType.EMAIL);
                    account.getCredentials().add(emailCredential);
                }

                // apply all pending changes
                if (pendingToken.getEmail() != null) {
                    final long count = accountService.query()
                            .filter("credentials.type =", AccountCredentialType.EMAIL)
                            .filter("credentials.key =", pendingToken.getEmail())
                            .countAll();

                    if (count == 0) {
                        emailCredential.setKey(pendingToken.getEmail());
                    }
                }
                if (pendingToken.getSecret() != null) {
                    emailCredential.setSecret(pendingToken.getSecret());
                    emailCredential.setSalt(pendingToken.getSalt());
                }
                // reset token
                account.setPendingToken(null);
            }
            // save account changes and authenticate
            accountService.save(account);
            return true;
        }

        // pending token matches
        if (submittedToken.equals(secondaryToken)) {
            final Account account = getAccount(token);
            final AccountPendingToken pendingToken = account.getPendingToken();
            // reset pending token
            pendingToken.setSecondaryToken(null);
            // save account changes
            accountService.save(account);
            // can not be authenticated as it comes not from original email
            return false;
        }

        // tokens doesn't match
        return false;
    }

    private Account getAccount(final AuthenticationToken token) {
        final ObjectId userId = ((Token) token).getPrincipal();
        return userId != null ? accountService.get(userId) : null;
    }

    public static class Token implements AuthenticationToken {
        private final ObjectId userId;
        private final String token;

        public Token(final ObjectId userId, final String token) {
            this.userId = userId;
            this.token = token;
        }

        public ObjectId getPrincipal() {
            return userId;
        }

        public String getCredentials() {
            return token;
        }
    }
}
