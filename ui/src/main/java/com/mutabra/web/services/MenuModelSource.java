package com.mutabra.web.services;

import com.mutabra.web.internal.model.MenuModel;
import org.apache.tapestry5.ioc.Messages;

/**
 * @author Ivan Khalopik
 */
public interface MenuModelSource {

    public <T extends Enum<T> & Item>
    MenuModel buildForEnum(Messages messages, Class<T> enumClass);


    public interface Item {

        Class<?> getPageClass();

        Class<? extends Item> getChildMenuClass();
    }
}
