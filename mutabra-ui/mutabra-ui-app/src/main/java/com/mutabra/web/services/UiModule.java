package com.mutabra.web.services;

import org.apache.tapestry5.ioc.annotations.SubModule;

/**
 * @author Ivan Khalopik
 */
@SubModule({ServicesModule.class, SecurityModule.class, ApplicationModule.class})
public class UiModule {
}
