/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.components.menu;

import com.mutabra.domain.game.Account;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.Index;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserMenu extends AbstractComponent {

    @Inject
    private AccountContext accountContext;

    public String getUserName() {
        final Account account = accountContext.getAccount();
        return account.getHero() != null ?
                account.getHero().getAppearance().getName() :
                account.getName();
    }

    @OnEvent("signOut")
    Object signOut() {
        getSubject().logout();
        return Index.class;
    }
}
