package com.mutabra.web.components.menu;

import com.mutabra.domain.game.Account;
import com.mutabra.domain.game.Hero;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.pages.Index;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserMenu extends AbstractComponent {

    @Inject
    private AccountContext accountContext;

    public Account getAccount() {
        return accountContext.getAccount();
    }

    public Hero getHero() {
        return accountContext.getHero();
    }

    public String getUserName() {
        final Hero hero = getHero();
        if (hero != null) {
            return hero.getName();
        }

        final Account account = getAccount();
        if (account.getName() != null) {
            return account.getName();
        }
        if (account.getEmail() != null) {
            return account.getEmail().replaceFirst("@.*$", "");
        }
        return "<unknown>";
    }

    Object onSignOut() {
        getSubject().logout();
        return Index.class;
    }
}
