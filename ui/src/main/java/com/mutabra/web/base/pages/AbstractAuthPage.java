package com.mutabra.web.base.pages;

import com.mutabra.web.pages.game.GameHome;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 */
public class AbstractAuthPage extends AbstractPage {

    @Inject
    private RequestGlobals globals;

    protected Object authenticated() throws IOException {
        final SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(globals.getHTTPServletRequest());
        if (savedRequest != null && "GET".equals(savedRequest.getMethod())) {
            final String savedUrl = savedRequest.getRequestUrl();
            globals.getResponse().sendRedirect(savedUrl);
            return null;
        }

        // if there are no saved redirect page
        // user will be redirected to game home page
        return GameHome.class;
    }
}
