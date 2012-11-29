package com.mutabra.web.pages;

import com.mutabra.domain.game.Account;
import com.mutabra.security.OAuth;
import com.mutabra.services.BaseEntityService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.internal.security.ConfirmationRealm;
import com.mutabra.web.internal.security.FacebookRealm;
import com.mutabra.web.internal.security.GoogleRealm;
import com.mutabra.web.internal.security.TwitterRealm;
import com.mutabra.web.internal.security.VKRealm;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.MailService;
import com.mutabra.web.services.PasswordGenerator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import java.io.IOException;

import static com.mutabra.services.Mappers.account$;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Security extends AbstractPage {
    private static final String APPLY_EVENT = "apply";

    private static final String SIGN_IN_TAB = "signIn";
    private static final String SIGN_UP_TAB = "signUp";
    private static final String RESTORE_TAB = "restore";

    @Property
    private String email;

    @Property
    private String password;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String activeTab;

    private Account account;

    @InjectComponent
    private Form signInForm;

    @InjectComponent
    private Form signUpForm;

    @InjectComponent
    private Form restoreForm;

    @InjectService("accountService")
    private BaseEntityService<Account> accountService;

    @Inject
    private PasswordGenerator generator;

    @Inject
    private MailService mailService;

    @Inject
    private RequestGlobals globals;

    public Link createApplyChangesLink(final Long userId, final String token) {
        return getResources().createEventLink(APPLY_EVENT, userId, token);
    }

    @OnEvent(EventConstants.ACTIVATE)
    void activatePage() {
        final Request request = globals.getRequest();
        if (request.getParameter("error") != null) {
            // error request parameter means that authentication failed
            signInForm.clearErrors();
            signInForm.recordError(message("error.sign-in"));
        } else if (request.getParameter("not_authenticated") != null) {
            // not_authenticated request parameter means that user is not authenticated
            signInForm.clearErrors();
            signInForm.recordError(message("error.sign-in.not-authenticated"));
        }
        // setup default tab if not specified
        if (activeTab == null) {
            activeTab = SIGN_IN_TAB;
        }
    }

    @OnEvent(value = EventConstants.PREPARE_FOR_SUBMIT, component = "signInForm")
    void activateSignInTab() {
        // keep in mind that current tab is sign in
        activeTab = SIGN_IN_TAB;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "signInForm")
    Object signIn() throws IOException {
        try {
            // try to sign in
            final String remoteHost = globals.getRequest().getRemoteHost();
            getSubject().login(new UsernamePasswordToken(email, password, remoteHost));
            // if authentication is successful user will be redirected to earlier requested page
            final SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(globals.getHTTPServletRequest());
            if (savedRequest != null && "GET".equals(savedRequest.getMethod())) {
                final String savedUrl = savedRequest.getRequestUrl();
                globals.getResponse().sendRedirect(savedUrl);
                return null;
            }
            // if there are no saved redirect page
            // user will be redirected to game home page
            return GameHome.class;
        } catch (AuthenticationException e) {
            // if authentication was failed user will stay on this page with reported error
            signInForm.recordError(message("error.sign-in"));
        }
        return null;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "facebook")
    Object facebookConnected(final OAuth.Session session) {
        getSubject().login(new FacebookRealm.Token(session));
        return GameHome.class;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "twitter")
    Object twitterConnected(final OAuth.Session session) {
        getSubject().login(new TwitterRealm.Token(session));
        return GameHome.class;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "google")
    Object googleConnected(final OAuth.Session session) {
        getSubject().login(new GoogleRealm.Token(session));
        return GameHome.class;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "vk")
    Object vkConnected(final OAuth.Session session) {
        getSubject().login(new VKRealm.Token(session));
        return GameHome.class;
    }

    @OnEvent(value = EventConstants.PREPARE_FOR_SUBMIT, component = "signUpForm")
    void activateSignUpTab() {
        // keep in mind that current tab is sign up
        activeTab = SIGN_UP_TAB;
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "signUpForm")
    void validateSignUpForm() {
        account = accountService.query()
                .filter(account$.email$.eq(email))
                .unique();
        if (account != null) {
            // user with specified email doesn't exist
            signUpForm.recordError(message("error.sign-up"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "signUpForm")
    Object signUp() {
        account = accountService.create();
        // we should generate new password
        // and create auth token to confirm password changes
        // when user will confirm this from his email new password will be applied
        // and he will be automatically authenticated
        final String password = generator.generateSecret();
        final Hash hash = generator.generateHash(password);

        final String token = generator.generateSecret();
        final long expired = generator.generateExpirationTime();

        account.setEmail(email);

        account.setPendingPassword(hash.toBase64());
        account.setPendingSalt(hash.getSalt().toBase64());

        account.setToken(token);
        account.setTokenExpired(expired);
        accountService.saveOrUpdate(account);

        final Link link = createApplyChangesLink(account.getId(), token);
        mailService.send(
                account.getEmail(),
                message("mail.sign-up.title"),
                format("mail.sign-up.body", account.getEmail(), password, link.toAbsoluteURI()));
        //todo: add mail sent notification
        return Index.class;
    }

    @OnEvent(value = EventConstants.PREPARE_FOR_SUBMIT, component = "restoreForm")
    void activateRestoreTab() {
        // keep in mind that current tab is restore
        activeTab = RESTORE_TAB;
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "restoreForm")
    void validateRestoreForm() {
        account = accountService.query()
                .filter(account$.email$.eq(email))
                .unique();
        if (account == null) {
            // user with specified email doesn't exist
            restoreForm.recordError(message("error.restore-password"));
        } else if (account.getTokenExpired() != null &&
                account.getTokenExpired() > System.currentTimeMillis()) {
            // user already has pending changes
            restoreForm.recordError(message("error.restore-password"));
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

        account.setPendingPassword(hash.toBase64());
        account.setPendingSalt(hash.getSalt().toBase64());

        account.setToken(token);
        account.setTokenExpired(expired);
        accountService.saveOrUpdate(account);

        final Link link = createApplyChangesLink(account.getId(), token);
        mailService.send(
                account.getEmail(),
                message("mail.restore-password.title"),
                format("mail.restore-password.body", account.getEmail(), password, link.toAbsoluteURI()));
        //todo: add mail sent notification
        return Index.class;
    }

    @OnEvent(APPLY_EVENT)
    Object applyPendingChanges(final Long userId, final String token) {
        getSubject().login(new ConfirmationRealm.Token(userId, token));
        //todo: add notification when pending changes was confirmed
        return GameHome.class;
    }
}
