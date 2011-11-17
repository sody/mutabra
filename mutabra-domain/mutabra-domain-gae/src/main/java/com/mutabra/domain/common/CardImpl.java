package com.mutabra.domain.common;

import com.googlecode.objectify.Key;
import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.Keys;
import com.mutabra.domain.TranslationType;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.CARD)
public class CardImpl extends CodedEntityImpl implements Card {

	private CardType type;

	private Key<LevelImpl> level;

	@Embedded
	private EffectImpl effect = new EffectImpl();

	@Embedded
	private SummonImpl summon = new SummonImpl();

	public CardImpl() {
		this(null, CardType.UNKNOWN);
	}

	public CardImpl(final String code, final CardType type) {
		super(Tables.CARD, code, TranslationType.DESCRIPTION);
		this.type = type;
		if (type == CardType.EFFECT) {
			effect = new EffectImpl();
		}
		if (type == CardType.SUMMON) {
			summon = new SummonImpl();
		}
	}

	public CardType getType() {
		return type;
	}

	public Level getLevel() {
		return Keys.getInstance(level);
	}

	public void setLevel(final Level level) {
		this.level = new Key<LevelImpl>(LevelImpl.class, level.getId());
	}

	public Effect getEffect() {
		return effect;
	}

	public Summon getSummon() {
		return summon;
	}
}
