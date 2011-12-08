package com.mutabra.domain.common;

import com.googlecode.objectify.Key;
import com.mutabra.db.Tables;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;
import com.mutabra.scripts.FakeScript;

import javax.persistence.Entity;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.CARD)
public class CardImpl extends CastableImpl implements Card {

	private Key<LevelImpl> level;

	public CardImpl() {
		this(null);
	}

	public CardImpl(final String code) {
		super(Tables.CARD, code, TranslationType.DESCRIPTION, FakeScript.class.getName());
	}

	public Level getLevel() {
		return Keys.getInstance(level);
	}

	public void setLevel(final Level level) {
		this.level = Keys.getKey(level);
	}

	public Set<Ability> getAbilities() {
		return Keys.getChildren(Ability.class, AbilityImpl.class, this);
	}
}
