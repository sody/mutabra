package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleEffect;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.BattleState;
import com.mutabra.domain.battle.BattleUnit;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.Castable;
import com.mutabra.domain.common.Effect;
import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.scripts.ScriptExecutor;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleServiceImpl extends BaseEntityServiceImpl<Battle> implements BattleService {
	private static final Position[] DUEL_POSITIONS = {
			new Position(0, 0),
			new Position(0, 1),
			new Position(1, 0),
			new Position(1, 2),
			new Position(2, 0),
			new Position(2, 1),
	};

	private final ScriptExecutor scriptExecutor;
	private final Class<? extends BattleHero> realHeroClass;
	private final Class<? extends BattleEffect> realEffectClass;

	public BattleServiceImpl(final EntityRepository repository, final ScriptExecutor scriptExecutor) {
		super(repository, Battle.class);
		this.scriptExecutor = scriptExecutor;

		realHeroClass = repository.create(BattleHero.class).getClass();
		realEffectClass = repository.create(BattleEffect.class).getClass();
	}

	@Transactional
	public void startBattle(final Hero hero1, final Hero hero2) {
		final Battle battle = create();
		battle.setStartedAt(new Date());
		battle.setRound(1);
		battle.setState(BattleState.STARTED);
		save(battle);

		addMember(battle, hero1, new Position(1, 2));
		addMember(battle, hero2, new Position(1, 0));

		hero1.setBattle(battle);
		hero2.setBattle(battle);
		repository().save(hero1);
		repository().save(hero2);
	}

	public void registerAction(final Battle battle,
							   final BattleUnit caster,
							   final Castable castable,
							   final Position target) {

		for (Effect effect : castable.getEffects()) {
			final BattleEffect battleEffect = ReflectionUtils.newInstance(realEffectClass, battle, effect);
			battleEffect.setCaster(caster);
			battleEffect.setTarget(target);
			battleEffect.setDuration(effect.getDuration());
			repository().save(battleEffect);
		}
		caster.setHealth(caster.getHealth() - castable.getBloodCost());
		caster.setExhausted(true);
		if (caster instanceof BattleHero) {
			final BattleHero hero = (BattleHero) caster;
			final List<Card> hand = hero.getHand();
			final List<Card> graveyard = hero.getGraveyard();
			hand.remove((Card) castable);
			graveyard.add((Card) castable);
			hero.setHand(hand);
			hero.setGraveyard(graveyard);
		}
		repository().save(caster);

		for (BattleHero battleMember : battle.getHeroes()) {
			if (!battleMember.isExhausted()) {
				return;
			}
			for (BattleCreature battleCreature : battleMember.getCreatures()) {
				if (!battleCreature.isExhausted()) {
					return;
				}
			}
		}
		endRound(battle);
	}

	@Transactional
	public void endRound(final Battle battle) {
		final List<BattleEffect> effects = battle.getEffects();
		for (BattleEffect battleEffect : effects) {
			final BattleUnit caster = battleEffect.getCaster();
			final Effect effect = battleEffect.getEffect();
			final BattleHero hero = caster instanceof BattleCreature
					? ((BattleCreature) caster).getOwner()
					: (BattleHero) caster;

			final List<BattleField> battleField = getBattleField(hero.getHero(), battle);
			final List<?> targets = getTargets(effect.getTargetType(), battleField, battleEffect.getTarget());
			scriptExecutor.executeScript(caster, effect, targets);
			battleEffect.setDuration(battleEffect.getDuration() - 1);
			if (battleEffect.getDuration() <= 0) {
				repository().delete(battleEffect);
			} else {
				repository().save(battleEffect);
			}
			for (Object target : targets) {
				if (target instanceof BattleUnit) {
					repository().save((BattleUnit) target);
				}
			}
		}

		battle.setRound(battle.getRound() + 1);
		save(battle);
		for (BattleHero hero : battle.getHeroes()) {
			hero.setExhausted(false);
			hero.setMentalPower(hero.getMentalPower() + 1);
			final List<Card> deck = hero.getDeck();
			final List<Card> hand = hero.getHand();
			if (deck.size() > 0) {
				hand.add(deck.remove(0));
			}
			hero.setDeck(deck);
			hero.setHand(hand);
			repository().save(hero);
			for (BattleCreature creature : hero.getCreatures()) {
				creature.setExhausted(false);
				repository().save(creature);
			}
		}
	}

	public List<BattleField> getBattleField(final Hero hero, final Battle battle) {
		final Map<Position, BattleField> battleField = new HashMap<Position, BattleField>();
		boolean upSide = false;
		for (BattleHero member : battle.getHeroes()) {
			final boolean enemy = !member.getHero().equals(hero);
			if (!enemy && member.getPosition().getY() == 0) {
				upSide = true;
			}
			battleField.put(member.getPosition(), new BattleField(member, enemy));
			for (BattleCreature creature : member.getCreatures()) {
				battleField.put(creature.getPosition(), new BattleField(creature, enemy));
			}
		}

		for (Position position : DUEL_POSITIONS) {
			if (!battleField.containsKey(position)) {
				final boolean enemy = (position.getY() == 0 && !upSide) || (position.getY() > 0 && upSide);
				battleField.put(position, new BattleField(position, enemy));
			}
		}

		return new ArrayList<BattleField>(battleField.values());
	}

	private void addMember(final Battle battle, final Hero hero, final Position position) {
		final BattleHero battleHero = ReflectionUtils.newInstance(realHeroClass, battle, hero);
		battleHero.setHealth(hero.getHealth());
		battleHero.setMentalPower(10);
		battleHero.setPosition(position);
		battleHero.setExhausted(false);

		final List<Card> deck = new ArrayList<Card>();
		for (HeroCard card : hero.getCards()) {
			deck.add(card.getCard());
		}
		Collections.shuffle(deck);

		final List<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < deck.size() && i < 3; i++) {
			hand.add(deck.remove(0));
		}

		battleHero.setDeck(deck);
		battleHero.setHand(hand);
		repository().save(battleHero);
	}

	private List<?> getTargets(final TargetType targetType, final List<BattleField> battleFields, final Position position) {
		final ArrayList<Object> targets = new ArrayList<Object>();
		if (targetType.isMassive()) {
			for (BattleField field : battleFields) {
				if (field.supports(targetType)) {
					if (field.hasHero()) {
						targets.add(field.getHero());
					} else if (field.hasSummon()) {
						targets.add(field.getCreature());
					} else {
						targets.add(field.getPosition());
					}
				}
			}
		} else {
			for (BattleField field : battleFields) {
				if (field.supports(targetType) && field.getPosition().equals(position)) {
					if (field.hasHero()) {
						targets.add(field.getHero());
					} else if (field.hasSummon()) {
						targets.add(field.getCreature());
					} else {
						targets.add(field.getPosition());
					}
					break;
				}
			}
		}
		return targets;
	}
}
