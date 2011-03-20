package com.mutabra.web.services;

import com.mutabra.game.BattleService;
import com.mutabra.game.BattleServiceImpl;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Dependency;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Dependency( { ServicesModule.class, GameSecurityModule.class })
public class GAModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(BattleService.class, BattleServiceImpl.class);
	}
}
