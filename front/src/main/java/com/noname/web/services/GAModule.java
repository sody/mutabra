package com.noname.web.services;

import com.noname.game.BattleService;
import com.noname.game.BattleServiceImpl;
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
