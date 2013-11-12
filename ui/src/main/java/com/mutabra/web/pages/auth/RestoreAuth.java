/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.auth;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.AccountPendingToken;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.annotations.AuthMenuItem;
import com.mutabra.web.pages.Index;
import com.mutabra.web.services.MailService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.PageRenderLinkSource;

import static com.mutabra.web.internal.annotations.AuthMenuItemName.RESTORE;

/**
 * @author Ivan Khalopik
 */
@AuthMenuItem(RESTORE)
public class RestoreAuth extends AbstractAuthPage {

    @Property
    private String email;

    private Account account;

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @Inject
    private PasswordGenerator generator;

    @Inject
    private MailService mailService;

    @Inject
    private PageRenderLinkSource linkSource;

    @OnEvent(value = EventConstants.VALIDATE, component = "restoreForm")
    void validateRestoreForm() throws ValidationException {
        account = accountService.query()
                .filter("credentials.type =", AccountCredentialType.EMAIL)
                .filter("credentials.key =", email)
                .get();
        if (account == null) {
            // user with specified email doesn't exist
            throw new ValidationException(message("error.restore-password"));
        } else if (account.getPendingToken() != null && !account.getPendingToken().isExpired()) {
            // user already has pending changes
            throw new ValidationException(message("error.restore-password"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "restoreForm")
    Object restorePassword() {
        // we should generate new password
        // and create auth token to confirm password changes
        // when user will confirm this from his email new password will be applied
        // and he will be automatically authenticated
        final String password = generator.generateSecret();
        final Hash hash = generator.generateHash(password);

        final String token = generator.generateSecret();
        final long expired = generator.generateExpirationTime();

        // generate pending token
        final AccountPendingToken pendingToken = new AccountPendingToken();
        pendingToken.setToken(token);
        pendingToken.setExpiredAt(expired);
        pendingToken.setSecret(hash.toBase64());
        pendingToken.setSalt(hash.getSalt().toBase64());
        account.setPendingToken(pendingToken);

        // save account
        accountService.save(account);

        // send mail with confirmation link
        final Link link = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);
        mailService.send(
                email,
                message("mail.restore-password.title"),
                format("mail.restore-password.body", email, password, link.toAbsoluteURI()));
        //todo: add mail sent notification
        return Index.class;
    }
}
