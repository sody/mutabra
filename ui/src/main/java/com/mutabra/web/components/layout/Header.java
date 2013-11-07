package com.mutabra.web.components.layout;

import com.mutabra.domain.game.Account;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.game.GameHome;
import com.mutabra.web.services.AccountContext;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Header extends AbstractComponent {

    @Inject
    private AccountContext accountContext;

    @Property
    private String email;

    @Property
    private String password;

    public Account getAccount() {
        return accountContext.getAccount();
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "signIn")
    Object signIn() {
        getSubject().login(new UsernamePasswordToken(email, password));
        return GameHome.class;
    }
}
