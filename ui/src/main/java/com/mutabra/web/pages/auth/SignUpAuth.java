/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.pages.auth;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.AccountCredential;
import com.mutabra.domain.game.AccountCredentialType;
import com.mutabra.domain.game.AccountPendingToken;
import com.mutabra.domain.game.Role;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.annotations.AuthMenu;
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

import static com.mutabra.web.internal.annotations.AuthMenuItem.SIGN_UP;

/**
 * @author Ivan Khalopik
 */
@AuthMenu(SIGN_UP)
public class SignUpAuth extends AbstractAuthPage {

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

    @OnEvent(value = EventConstants.VALIDATE, component = "signUpForm")
    void validateSignUpForm() throws ValidationException {
        account = accountService.query()
                .filter("credentials.type =", AccountCredentialType.EMAIL)
                .filter("credentials.key =", email)
                .get();
        if (account != null) {
            // user with specified email doesn't exist
            throw new ValidationException(message("error.sign-up"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "signUpForm")
    Object signUp() {
        account = new Account();
        account.setName(email.substring(0, email.indexOf('@')));
        // newly created user should be of role USER
        account.setRole(Role.USER);

        // we should generate new password
        // and create auth token to confirm password changes
        // when user will confirm this from his email new password will be applied
        // and he will be automatically authenticated
        final String password = generator.generateSecret();
        final Hash hash = generator.generateHash(password);

        final String token = generator.generateSecret();
        final long expired = generator.generateExpirationTime();

        // add email credential
        final AccountCredential credential = new AccountCredential();
        credential.setType(AccountCredentialType.EMAIL);
        credential.setKey(email);
        account.getCredentials().add(credential);

        // generate pending token
        final AccountPendingToken pendingChange = new AccountPendingToken();
        pendingChange.setToken(token);
        pendingChange.setExpiredAt(expired);
        pendingChange.setSecret(hash.toBase64());
        pendingChange.setSalt(hash.getSalt().toBase64());
        account.setPendingToken(pendingChange);

        // save account
        accountService.save(account);

        // send mail with confirmation link
        final Link link = linkSource.createPageRenderLinkWithContext(TokenAuth.class, account.getId(), token);

        mailService.send(
                email,
                message("mail.sign-up.title"),
                format("mail.sign-up.body", email, password, link.toAbsoluteURI()));
        //todo: add mail sent notification
        return Index.class;
    }

}
