package com.noname.web.components.battle;

import com.noname.game.BattleField;
import com.noname.game.FieldLine;
import com.noname.game.Locatable;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

/**
 * @author ivan.khalopik@tieto.com
 * @since 1.0
 */
public class FieldDisplay {

	@Parameter(required = true)
	private BattleField field;

	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String position;

	@Property
	private List<Locatable> backLine;

	@Property
	private List<Locatable> frontLine;

	@Property
	private Locatable locatable;

	void setupRender() {
		backLine = field.getLine(FieldLine.BACK);
		frontLine = field.getLine(FieldLine.FRONT);
	}
}
