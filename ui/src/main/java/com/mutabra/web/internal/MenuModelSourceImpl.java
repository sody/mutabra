package com.mutabra.web.internal;

import com.mutabra.web.internal.model.MenuModel;
import com.mutabra.web.internal.model.MenuModelBuilder;
import com.mutabra.web.internal.security.SecurityFilter;
import com.mutabra.web.internal.security.SecurityHelper;
import com.mutabra.web.services.MenuModelSource;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author Ivan Khalopik
 */
public class MenuModelSourceImpl extends SecurityHelper implements MenuModelSource {
    private final MetaDataLocator locator;
    private final ComponentClassResolver resolver;
    private final PageRenderLinkSource linkSource;

    public MenuModelSourceImpl(final MetaDataLocator locator,
                               final ComponentClassResolver resolver,
                               final PageRenderLinkSource linkSource) {
        this.locator = locator;
        this.resolver = resolver;
        this.linkSource = linkSource;
    }

    @Override
    public <T extends Enum<T> & Item>
    MenuModel buildForEnum(final Messages messages, final Class<T> enumClass) {
        final MenuModelBuilder builder = MenuModelBuilder.create();
        for (T enumValue : enumClass.getEnumConstants()) {
            buildItem(messages, builder.item(enumValue.name()), enumValue);
        }

        return builder.build();
    }

    private <T extends Item>
    void buildItem(final Messages messages,
                   final MenuModelBuilder.ItemBuilder builder,
                   final T item) {
        final Class<?> pageClass = item.getPageClass();
        final String pageName = pageClass != null ?
                resolver.resolvePageClassNameToPageName(pageClass.getName()) : null;

        // do not add menu item if page is not accessible by current user
        if (hasAccess(pageName)) {
            builder.label(MutabraInternalUtils.getLabel(messages, (Enum<?>) item))
                    .link(pageName != null ? linkSource.createPageRenderLink(pageName) : null);

            if (item.getChildMenuClass() != null) {
                for (Item childItem : item.getChildMenuClass().getEnumConstants()) {
                    buildItem(messages, builder.child(((Enum<?>) childItem).name()), childItem);
                }
            }
            builder.end();
        }
    }

    private boolean hasAccess(final String pageName) {
        final boolean authenticated = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_AUTHENTICATION_META, pageName, Boolean.class);
        if (authenticated && !isAuthenticated()) {
            return false;
        }

        final boolean user = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_USER_META, pageName, Boolean.class);
        if (user && !isUser()) {
            return false;
        }

        final boolean guest = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_GUEST_META, pageName, Boolean.class);
        if (guest && !isGuest()) {
            return false;
        }

        final String[] roles = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_ROLES_META, pageName, String[].class);
        if (roles != null) {
            final boolean logical = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_ROLES_LOGICAL_META, pageName, Boolean.class);
            if (!hasRoles(logical ? Logical.AND : Logical.OR, roles)) {
                return false;
            }
        }

        final String[] permissions = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_META, pageName, String[].class);
        if (permissions != null) {
            final boolean logical = locator.findMeta(SecurityFilter.SHIRO_REQUIRES_PERMISSIONS_LOGICAL_META, pageName, Boolean.class);
            if (!isPermitted(logical ? Logical.AND : Logical.OR, permissions)) {
                return false;
            }
        }

        return true;
    }
}
