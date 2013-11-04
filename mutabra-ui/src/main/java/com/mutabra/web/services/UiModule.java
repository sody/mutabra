package com.mutabra.web.services;

import org.apache.tapestry5.ioc.annotations.SubModule;

/**
 * @author Ivan Khalopik
 */
@SubModule({
        DomainModule.class,
        ServicesModule.class,
        SecurityModule.class,
        MailModule.class,
        ApplicationModule.class})
public class UiModule {
}
