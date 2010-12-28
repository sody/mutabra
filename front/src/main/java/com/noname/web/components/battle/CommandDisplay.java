package com.noname.web.components.battle;

import com.noname.game.BattleCommand;
import com.noname.game.BattlePlayer;
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
