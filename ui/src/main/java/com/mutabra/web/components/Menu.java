package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.model.MenuModel;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.CleanupRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.MetaDataLocator;

import java.util.Iterator;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Menu extends AbstractComponent {

    @Parameter(required = true, allowNull = false)
    private MenuModel model;

    private String active;
    private Iterator<MenuModel.Item> iterator;

    @Inject
    private MetaDataLocator locator;

    @SetupRender
    boolean setup(final MarkupWriter writer) {
        // find active menu item
        final ComponentResources pageResources = getResources().getPage().getComponentResources();
        active = locator.findMeta(MenuModel.ACTIVE_KEY, pageResources, String.class);

        final List<MenuModel.Item> source = model.getItems();
        iterator = source != null ? source.iterator() : null;

        // begin menu
        writer.element("ul");
        getResources().renderInformalParameters(writer);

        // continue if there are at least one menu item
        return iterator != null && iterator.hasNext();
    }

    @BeginRender
    void begin(final MarkupWriter writer) {
        final MenuModel.Item item = iterator.next();

        // begin menu item
        final Element element = writer.element("li");
        if (item.getId().equals(active)) {
            element.addClassName("active");
        }
        if (item.getChildren() != null) {
            element.addClassName("dropdown");

            // begin link
            writer.element("a",
                    "href", "#",
                    "class", "dropdown-toggle",
                    "data-toggle", "dropdown");

            writer.write(item.getLabel());
            writer.element("span", "class", "caret");
            writer.end();

            // end link
            writer.end();

            // begin child menu
            writer.element("ul", "class", "dropdown-menu");
            for (MenuModel.Item child : item.getChildren()) {
                writer.element("li");
                writer.element("a", "href", child.getLink().toURI());

                writer.write(child.getLabel());

                writer.end();
                writer.end();
            }
            // end child menu
            writer.end();
        } else {
            // begin link
            writer.element("a", "href", item.getLink().toURI());

            writer.write(item.getLabel());

            // end link
            writer.end();
        }
    }

    @AfterRender
    boolean end(final MarkupWriter writer) {
        // end menu item
        writer.end();

        return !iterator.hasNext();
    }

    @CleanupRender
    void cleanup(final MarkupWriter writer) {
        // end menu
        writer.end();
    }
}
