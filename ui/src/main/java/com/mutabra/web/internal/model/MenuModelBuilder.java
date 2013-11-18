package com.mutabra.web.internal.model;

import org.apache.tapestry5.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MenuModelBuilder {
    private final ItemBuilder root = new ItemBuilder();

    public ItemBuilder item(final String id) {
        return root.child(id);
    }

    public MenuModel build() {
        return new Model(root.children);
    }

    public static MenuModelBuilder create() {
        return new MenuModelBuilder();
    }

    public class ItemBuilder {
        private final ItemBuilder parent;
        private final String id;

        private String label;
        private Link link;
        private List<MenuModel.Item> children;

        private ItemBuilder() {
            this.parent = null;
            this.id = null;

            // create empty collection of menu items
            children = new ArrayList<MenuModel.Item>();
        }

        private ItemBuilder(final ItemBuilder parent, final String id) {
            this.parent = parent;
            this.id = id;
        }

        public ItemBuilder label(final String label) {
            this.label = label;
            return this;
        }

        public ItemBuilder link(final Link link) {
            this.link = link;
            return this;
        }

        public ItemBuilder child(final String id) {
            return new ItemBuilder(this, id);
        }

        public ItemBuilder end() {
            if (parent == null) {
                throw new UnsupportedOperationException("There are no more parents.");
            }

            parent.addChild(new Item(id, label, link, children));

            return parent;
        }

        private void addChild(final MenuModel.Item item) {
            if (children == null) {
                children = new ArrayList<MenuModel.Item>();
            }
            children.add(item);
        }
    }

    static class Item implements MenuModel.Item {
        private final String id;
        private final String label;
        private final Link link;
        private final List<MenuModel.Item> children;

        private Item(final String id,
                     final String label,
                     final Link link,
                     final List<MenuModel.Item> children) {
            this.id = id;
            this.label = label;
            this.link = link;
            this.children = children;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public Link getLink() {
            return link;
        }

        @Override
        public List<MenuModel.Item> getChildren() {
            return children;
        }
    }

    static class Model implements MenuModel {
        private final List<MenuModel.Item> items;

        private Model(final List<Item> items) {
            this.items = items;
        }

        @Override
        public List<MenuModel.Item> getItems() {
            return items;
        }
    }
}
