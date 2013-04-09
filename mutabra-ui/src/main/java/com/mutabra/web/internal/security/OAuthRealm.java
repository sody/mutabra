package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredential;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.Role;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.tapestry5.ioc.annotations.InjectService;

import java.util.Date;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OAuthRealm extends AuthenticatingRealm {
    private final BaseEntityService<Account> accountService;
    private final PasswordGenerator generator;

    public OAuthRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
                      final PasswordGenerator generator) {
        this.accountService = accountService;
        this.generator = generator;

        setAuthenticationTokenClass(Token.class);
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
            throws AuthenticationException {
        try {
            @SuppressWarnings("unchecked")
            final OAuth.Session session = ((Token) token).getSession();

            // retrieve oauth profile
            final Map<String, Object> profile = session != null ? session.getProfile() : null;
            // deny empty oauth profiles
            if (profile == null) {
                throw new AccountException("Empty profiles are not allowed by this realm.");
            }

            // retrieve oauth provider type and profileId
            final AccountCredentialType type = ((Token) token).getType();
            final String profileId = String.valueOf(profile.get(OAuth.Session.ID));
            // deny empty oauth profileId
            if (profileId == null || profileId.isEmpty()) {
                throw new AccountException("Invalid oauth identifier");
            }

            // retrieve existent account by oauth provider type and profileId
            Account account = getAccount(type, profileId);
            if (account != null) {
                account.setLastLogin(new Date());
                accountService.save(account);
                return new SimpleAuthenticationInfo(account.getId(), null, getName());
            }

            // retrieve existent account by email
            final String email = (String) profile.get(OAuth.Session.EMAIL);
            if (email != null && !email.isEmpty()) {
                account = getAccount(AccountCredentialType.EMAIL, email);

                // attach oauth credential to existent account
                if (account != null) {
                    final AccountCredential oauthCredential = new AccountCredential();
                    oauthCredential.setType(type);
                    oauthCredential.setKey(profileId);
                    account.getCredentials().add(oauthCredential);

                    account.setLastLogin(new Date());
                    accountService.save(account);
                    return new SimpleAuthenticationInfo(account.getId(), null, getName());
                }
            }

            // create new account from oauth profile
            account = new Account();
            // attach email credential with random secret
            if (email != null && !email.isEmpty()) {
                final AccountCredential emailCredential = new AccountCredential();
                emailCredential.setType(AccountCredentialType.EMAIL);
                emailCredential.setKey(email);

                // generate random secret
                final Hash hash = generator.generateHash();
                emailCredential.setSecret(hash.toBase64());
                if (hash.getSalt() != null) {
                    emailCredential.setSalt(hash.getSalt().toBase64());
                }

                account.getCredentials().add(emailCredential);
            }
            // fill account common properties
            account.setRegistered(new Date());
            account.setName((String) profile.get(OAuth.Session.NAME));
            // account name is required
            if (account.getName() == null || account.getName().isEmpty()) {
                account.setName("<unknown>");
            }
            //todo: account.setLocale(LocaleUtils.parseLocale((String) profile.get(OAuth.Session.LOCALE)));
            //todo: account.setTimeZone(...);
            //todo: account.setGender(...);
            // newly created account should be of role USER
            account.setRole(Role.USER);

            // attach oauth credential
            final AccountCredential oauthCredential = new AccountCredential();
            oauthCredential.setType(type);
            oauthCredential.setKey(profileId);
            account.getCredentials().add(oauthCredential);

            account.setLastLogin(new Date());
            accountService.save(account);
            return new SimpleAuthenticationInfo(account.getId(), null, getName());
        } catch (AuthenticationException ex) {
            // rethrow exception
            throw ex;
        } catch (RuntimeException ex) {
            // rethrow wrapped exception
            throw new AuthenticationException(ex.getMessage(), ex);
        }
    }

    private Account getAccount(final AccountCredentialType type, final String key) {
        return accountService.query()
                .filter("credentials.type =", type)
                .filter("credentials.key =", key)
                .get();
    }

    /**
     * @author Ivan Khalopik
     * @since 1.0
     */
    public static class Token implements AuthenticationToken {
        private final OAuth.Session session;
        private final AccountCredentialType type;

        public Token(final AccountCredentialType type, final OAuth.Session session) {
            this.session = session;
            this.type = type;
        }

        public AccountCredentialType getType() {
            return type;
        }

        public OAuth.Session getSession() {
            return session;
        }

        public Object getPrincipal() {
            return session;
        }

        public Object getCredentials() {
            return null;
        }
    }
}
