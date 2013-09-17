package com.mutabra.web.pages.auth;

import com.mutabra.web.base.pages.AbstractAuthPage;
import com.mutabra.web.internal.security.ConfirmationRealm;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 */
public class TokenAuth extends AbstractAuthPage {

    @OnEvent(value = EventConstants.ACTIVATE)
    Object authenticate(final ObjectId userId, final String token) throws IOException {
        getSubject().login(new ConfirmationRealm.Token(userId, token));

        return authenticated();
    }
}
