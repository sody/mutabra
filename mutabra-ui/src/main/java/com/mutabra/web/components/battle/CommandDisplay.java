package com.mutabra.web.components.battle;

import com.mutabra.game.BattleCommand;
import com.mutabra.game.BattlePlayer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class CommandDisplay {

	@Property
	@Parameter(required = true)
	private BattleCommand command;

	@Property
	private BattlePlayer player;
}
