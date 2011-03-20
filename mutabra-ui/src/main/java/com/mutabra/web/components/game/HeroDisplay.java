package com.mutabra.web.components.game;

import com.mutabra.domain.player.Hero;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
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
