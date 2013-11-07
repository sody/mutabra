package com.mutabra.web.internal.security;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredential;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.Role;
import com.mutabra.services.BaseEntityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.bson.types.ObjectId;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MainRealm extends AuthorizingRealm {
    private final BaseEntityService<Account> accountService;

    public MainRealm(final BaseEntityService<Account> accountService, final CredentialsMatcher credentialsMatcher) {
        super(credentialsMatcher);
        this.accountService = accountService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        final ObjectId userId = (ObjectId) getAvailablePrincipal(principals);

        if (userId == null) {
            throw new AccountException("Null user identifiers are not allowed by this realm.");
        }

        try {
            final Account account = accountService.get(userId);

            if (account == null) {
                return null;
            }

            final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            final Role role = account.getRole();
            info.addRole(role.name());
            info.addStringPermissions(role.getPermissions());

            return info;
        } catch (RuntimeException ex) {
            throw new AuthorizationException(ex.getMessage(), ex);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
        final String username = ((UsernamePasswordToken) token).getUsername();

        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        try {
            final Account account = accountService.query()
                    .filter("credentials.type =", AccountCredentialType.EMAIL)
                    .filter("credentials.key =", username)
                    .get();

            if (account == null) {
                return null;
            }
            final AccountCredential emailCredential = account.getCredentials(AccountCredentialType.EMAIL);

            final SimpleAuthenticationInfo info =
                    new SimpleAuthenticationInfo(account.getId(), emailCredential.getSecret(), getName());
            if (emailCredential.getSalt() != null) {
                final ByteSource salt = ByteSource.Util.bytes(Base64.decode(emailCredential.getSalt()));
                info.setCredentialsSalt(salt);
            }

            return info;
        } catch (RuntimeException ex) {
            throw new AuthenticationException(ex.getMessage(), ex);
        }
    }
}
