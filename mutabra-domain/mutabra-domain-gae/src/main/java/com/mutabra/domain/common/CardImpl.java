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
	private EffectImpl effect;

	@Embedded
	private SummonImpl summon;

	public CardImpl() {
		this(CardType.UNKNOWN);
	}

	protected CardImpl(final CardType type) {
		super(Tables.CARD, TranslationType.STANDARD);
		this.type = type;
	}

	public CardType getType() {
		return type;
	}

	public void setType(final CardType type) {
		this.type = type;
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

	public void setEffect(final Effect effect) {
		this.effect = (EffectImpl) effect;
	}

	public Summon getSummon() {
		return summon;
	}

	public void setSummon(final Summon summon) {
		this.summon = (SummonImpl) summon;
	}
}
