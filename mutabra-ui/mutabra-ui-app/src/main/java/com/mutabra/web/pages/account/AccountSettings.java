package com.mutabra.web.pages.account;

import com.mutabra.domain.game.Account;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.Security;
import com.mutabra.web.services.AccountContext;
import com.mutabra.web.services.MailService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
public class AccountSettings extends AbstractPage {

    @Property
    private String email;

    @Property
    private String password;

    @InjectComponent
    private Form changePasswordForm;

    @InjectComponent
    private Form changeEmailForm;

    @InjectPage
    private Security security;

    @Inject
    private AccountContext accountContext;

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @Inject
    private PasswordGenerator generator;

    @Inject
    private MailService mailService;

    public Account getValue() {
        return accountContext.getAccount();
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "accountForm")
    void save() {
        accountService.saveOrUpdate(getValue());
        //todo: add success notification
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "changeEmailForm")
    void validateChangeEmailForm() {
        final Account account = getValue();
        if (account == null) {
            // user is not authenticated(impossible)
            changeEmailForm.recordError(message("error.change-email.unknown"));
        } else if (account.getTokenExpired() != null &&
                account.getTokenExpired() > System.currentTimeMillis()) {
            // user already has pending changes
            changeEmailForm.recordError(message("error.change-email.try-again-later"));
        } else {
            final long count = accountService.query()
                    .filter(account$.email$.eq(email))
                    .count();
            if (count > 0) {
                // user with specified email is already exist
                changeEmailForm.recordError(message("error.change-email.unknown"));
            }
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changeEmailForm")
    void changeEmail() {
        final Account account = getValue();

        if (account.getEmail() == null) {
            // email is empty (user was created from social networks auth without provided email)
            // so we can create just one auth-token to confirm user email
            // todo: add warning with confirmation
            // when user will confirm this email new email will be applied
            // and he will be automatically authenticated
            final String token = generator.generateSecret();
            final long expired = generator.generateExpirationTime();

            account.setPendingEmail(email);

            account.setToken(token);
            account.setTokenExpired(expired);

            final Link link = security.createApplyChangesLink(account.getId(), token);
            mailService.send(
                    account.getPendingEmail(),
                    message("mail.change-email.title"),
                    format("mail.change-email.just-confirmation-body",
                            account.getPendingEmail(),
                            link.toAbsoluteURI()));
        } else {
            // we should create two auth-tokens to confirm both emails: current and new
            // when user will confirm this email only from old email he will be automatically authenticated
            // when user will confirm this email only from new email he will not be authenticated
            // when user will confirm this email from both emails new email will be applied
            // and he will be automatically authenticated
            final String token = generator.generateSecret();
            final String pendingToken = generator.generateSecret();
            final long expired = generator.generateExpirationTime();

            account.setPendingEmail(email);

            account.setToken(token);
            account.setPendingToken(pendingToken);
            account.setTokenExpired(expired);

            final Link link = security.createApplyChangesLink(account.getId(), token);
            mailService.send(
                    account.getEmail(),
                    message("mail.change-email.title"),
                    format("mail.change-email.body",
                            account.getEmail(),
                            account.getPendingEmail(),
                            link.toAbsoluteURI()));

            final Link pendingLink = security.createApplyChangesLink(account.getId(), pendingToken);
            mailService.send(
                    account.getPendingEmail(),
                    message("mail.change-email.title"),
                    format("mail.change-email.body",
                            account.getEmail(),
                            account.getPendingEmail(),
                            pendingLink.toAbsoluteURI()));
        }

        accountService.saveOrUpdate(account);
        //todo: add mail sent notification
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "changePasswordForm")
    void validateChangePasswordForm() {
        final Account account = getValue();
        if (account == null) {
            // user is not authenticated(impossible)
            changePasswordForm.recordError(message("error.change-password.unknown"));
        } else if (account.getTokenExpired() != null &&
                account.getTokenExpired() > System.currentTimeMillis()) {
            // user already has pending changes
            changePasswordForm.recordError(message("error.change-password.try-again-later"));
        } else if (account.getEmail() == null) {
            // can not change password when user email is not defined(impossible)
            changePasswordForm.recordError(message("error.change-password.empty-email"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changePasswordForm")
    void changePassword() {
        final Account account = getValue();

        // we should create auth token to confirm password changes
        // when user will confirm this from his email new password will be applied
        // and he will be automatically authenticated
        final String token = generator.generateSecret();
        final Hash hash = generator.generateHash(password);
        final long expired = generator.generateExpirationTime();

        account.setPendingPassword(hash.toBase64());
        account.setPendingSalt(hash.getSalt().toBase64());

        account.setToken(token);
        account.setTokenExpired(expired);

        final Link link = security.createApplyChangesLink(account.getId(), token);
        mailService.send(
                account.getEmail(),
                message("mail.change-password.title"),
                format("mail.change-password.body", link.toAbsoluteURI()));
        accountService.saveOrUpdate(account);
        //todo: add mail sent notification
    }
}
