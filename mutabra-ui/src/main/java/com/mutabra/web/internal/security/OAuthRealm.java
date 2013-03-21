package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
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
public abstract class OAuthRealm<T extends OAuthRealm.Token> extends AuthenticatingRealm {
    private final BaseEntityService<Account> accountService;
    private final PasswordGenerator generator;

    public OAuthRealm(final @InjectService("accountService") BaseEntityService<Account> accountService,
                      final PasswordGenerator generator,
                      final Class<T> authenticationTokenClass) {
        this.accountService = accountService;
        this.generator = generator;

        setAuthenticationTokenClass(authenticationTokenClass);
        setCredentialsMatcher(new AllowAllCredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token)
            throws AuthenticationException {
        try {
            @SuppressWarnings("unchecked")
            final OAuth.Session session = ((T) token).getSession();

            final Map<String, Object> profile = session != null ? session.getProfile() : null;
            if (profile == null) {
                throw new AccountException("Null profiles are not allowed by this realm.");
            }

            final String profileId = String.valueOf(profile.get(OAuth.Session.ID));
            if (profileId == null || profileId.isEmpty()) {
                throw new AccountException("Invalid facebook identifier");
            }
            Account account = getAccountByProfileId(profileId);
            if (account != null) {
                return fillAccount(account);
            }

            final String email = (String) profile.get(OAuth.Session.EMAIL);
            if (email != null && !email.isEmpty()) {
                account = getAccountByEmail(email);
                if (account != null) {
                    return attachAccount(account, profileId);
                }
            }

            return createAccount(profile);
        } catch (AuthenticationException ex) {
            // rethrow exception
            throw ex;
        } catch (RuntimeException ex) {
            // rethrow wrapped exception
            throw new AuthenticationException(ex.getMessage(), ex);
        }
    }

    protected abstract Account getAccountByProfileId(String profileId);

    protected abstract void setAccountProfileId(Account account, String profileId);

    protected Account getAccountByEmail(final String email) {
        return findAccount("email =", email);
    }

    protected Account findAccount(final String condition, final Object value) {
        return accountService.query().filter(condition, value).get();
    }

    protected AuthenticationInfo fillAccount(final Account account) {
        return new SimpleAuthenticationInfo(account.getId(), null, getName());
    }

    protected AuthenticationInfo attachAccount(final Account account, final String profileId) {
        setAccountProfileId(account, profileId);
        accountService.save(account);
        return fillAccount(account);
    }

    protected AuthenticationInfo createAccount(final Map<String, Object> profile) {
        final Account account = new Account();
        account.setEmail((String) profile.get(OAuth.Session.EMAIL));
        account.setRegistered(new Date());
        account.setName((String) profile.get(OAuth.Session.NAME));
        //todo: account.setLocale(LocaleUtils.parseLocale((String) profile.get(OAuth.Session.LOCALE)));
        //todo: account.setTimeZone(...);
        //todo: account.setGender(...);
        account.setRole(Role.USER);

        // generate random password
        final Hash hash = generator.generateHash();
        account.setPassword(hash.toBase64());
        if (hash.getSalt() != null) {
            account.setSalt(hash.getSalt().toBase64());
        }

        return attachAccount(account, String.valueOf(profile.get(OAuth.Session.ID)));
    }

    /**
     * @author Ivan Khalopik
     * @since 1.0
     */
    public static class Token implements AuthenticationToken {
        private final OAuth.Session session;

        public Token(final OAuth.Session session) {
            this.session = session;
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
