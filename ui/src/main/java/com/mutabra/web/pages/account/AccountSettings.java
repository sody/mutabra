/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.account;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.AccountPendingToken;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.pages.auth.TokenAuth;
import com.mutabra.web.services.AccountContext;
import com.mutabra.web.services.MailService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
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

    @Inject
    private AccountContext accountContext;

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @Inject
    private PasswordGenerator generator;

    @Inject
    private MailService mailService;

    @Inject
    private PageRenderLinkSource linkSource;

    public Account getValue() {
        return accountContext.getAccount();
    }

    public boolean isHasEmail() {
        return accountContext.getAccount().getCredentials(AccountCredentialType.EMAIL) != null;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "accountForm")
    void save() {
        accountService.save(getValue());
        //todo: add success notification
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "changeEmailForm")
    void validateChangeEmailForm() {
        final Account account = getValue();
        if (account == null) {
            // user is not authenticated(impossible)
            changeEmailForm.recordError(message("error.change-email.unknown"));
        } else if (account.getPendingToken() != null && !account.getPendingToken().isExpired()) {
            // user already has pending changes
            changeEmailForm.recordError(message("error.change-email.try-again-later"));
        } else {
            final long count = accountService.query()
                    .filter("credentials.type =", AccountCredentialType.EMAIL)
                    .filter("credentials.key =", email)
                    .countAll();
            if (count > 0) {
                // user with specified email is already exist
                changeEmailForm.recordError(message("error.change-email.unknown"));
            }
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "changeEmailForm")
    void changeEmail() {
        final Account account = getValue();

        if (account.getCredentials(AccountCredentialType.EMAIL) == null) {
            // email is empty (user was created from social networks auth without provided email)
            // so we can create just one auth-token to confirm user email
            // todo: add warning with confirmation
            // when user will confirm this email new email will be applied
            // and he will be automatically authenticated
            final String token = generator.generateSecret();
            final long expired = generator.generateExpirationTime();

            // generate pending token
            final AccountPendingToken pendingToken = new AccountPendingToken();
            pendingToken.setToken(token);
            pendingToken.setExpiredAt(expired);
            pendingToken.setEmail(email);
            account.setPendingToken(pendingToken);

            // send mail with confirmation link
            final Link link = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);
            mailService.send(
                    email,
                    message("mail.change-email.title"),
                    format("mail.change-email.just-confirmation-body",
                            email,
                            link.toAbsoluteURI()));
        } else {
            // we should create two auth-tokens to confirm both emails: current and new
            // when user will confirm this email only from old email he will be automatically authenticated
            // when user will confirm this email only from new email he will not be authenticated
            // when user will confirm this email from both emails new email will be applied
            // and he will be automatically authenticated
            final String token = generator.generateSecret();
            final String secondaryToken = generator.generateSecret();
            final long expired = generator.generateExpirationTime();

            // generate pending token
            final AccountPendingToken pendingToken = new AccountPendingToken();
            pendingToken.setToken(token);
            pendingToken.setSecondaryToken(secondaryToken);
            pendingToken.setExpiredAt(expired);
            pendingToken.setEmail(email);
            account.setPendingToken(pendingToken);

            final String accountEmail = account.getCredentials(AccountCredentialType.EMAIL).getKey();
            // send mail with confirmation link to old email
            final Link link = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);
            mailService.send(
                    accountEmail,
                    message("mail.change-email.title"),
                    format("mail.change-email.body",
                            accountEmail,
                            email,
                            link.toAbsoluteURI()));

            // send mail with confirmation link to new email
            final Link pendingLink = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);
            mailService.send(
                    email,
                    message("mail.change-email.title"),
                    format("mail.change-email.body",
                            accountEmail,
                            email,
                            pendingLink.toAbsoluteURI()));
        }

        accountService.save(account);
        //todo: add mail sent notification
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "changePasswordForm")
    void validateChangePasswordForm() {
        final Account account = getValue();
        if (account == null) {
            // user is not authenticated(impossible)
            changePasswordForm.recordError(message("error.change-password.unknown"));
        } else if (account.getPendingToken() != null && !account.getPendingToken().isExpired()) {
            // user already has pending changes
            changePasswordForm.recordError(message("error.change-password.try-again-later"));
        } else if (account.getCredentials(AccountCredentialType.EMAIL) == null) {
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

        // generate pending token
        final AccountPendingToken pendingToken = new AccountPendingToken();
        pendingToken.setToken(token);
        pendingToken.setExpiredAt(expired);
        pendingToken.setSecret(hash.toBase64());
        pendingToken.setSalt(hash.getSalt().toBase64());
        account.setPendingToken(pendingToken);

        final String accountEmail = account.getCredentials(AccountCredentialType.EMAIL).getKey();
        // send mail with confirmation link
        final Link link = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);
        mailService.send(
                accountEmail,
                message("mail.change-password.title"),
                format("mail.change-password.body", link.toAbsoluteURI()));
        accountService.save(account);
        //todo: add mail sent notification
    }
}
