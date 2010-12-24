package com.noname.web.components.game;

import com.noname.domain.player.Hero;
import com.noname.game.BattlePlayer;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class HeroDisplay {

	@Property
	@Parameter(required = true)
	private Hero hero;

	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String size;
}
