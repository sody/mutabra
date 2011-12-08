package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleAction;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCardState;
import com.mutabra.domain.battle.BattleField;
import com.mutabra.domain.battle.BattleMember;
import com.mutabra.domain.battle.BattleState;
import com.mutabra.domain.battle.BattleSummon;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.Card;
import com.mutabra.domain.common.TargetType;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.scripts.ScriptExecutor;
import com.mutabra.services.BaseEntityServiceImpl;
import org.greatage.domain.EntityRepository;
import org.greatage.domain.annotations.Transactional;
import org.greatage.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
	private final Class<? extends BattleMember> realMemberClass;
	private final Class<? extends BattleCard> realCardClass;
	private final Class<? extends BattleAction> realActionClass;

	public BattleServiceImpl(final EntityRepository repository, final ScriptExecutor scriptExecutor) {
		super(repository, Battle.class);
		this.scriptExecutor = scriptExecutor;

		realMemberClass = repository.create(BattleMember.class).getClass();
		realCardClass = repository.create(BattleCard.class).getClass();
		realActionClass = repository.create(BattleAction.class).getClass();
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

	@Transactional
	public void endRound(final Battle battle) {
		final Set<BattleAction> actions = battle.getActions();
		for (BattleAction action : actions) {
			final BattleCard card = action.getCard();
			if (card != null) {
				final Card realCard = card.getCard().getCard();
				final List<BattleField> battleField = getBattleField(card.getOwner().getHero(), battle);
				final List<?> targets = getTargets(realCard.getTargetType(), battleField, action.getTarget());
				scriptExecutor.executeScript(realCard, targets);
				card.setState(BattleCardState.GRAVEYARD);
				repository().save(card);
				for (Object target : targets) {
					if (target instanceof BattleMember) {
						repository().save((BattleMember) target);
					}
				}
			}
			//todo: process it
		}
		battle.setRound(battle.getRound() + 1);
		save(battle);
		for (BattleMember member : battle.getMembers()) {
			member.setExhausted(false);
			repository().save(member);
			for (BattleSummon summon : member.getSummons()) {
				summon.setExhausted(false);
				repository().save(summon);
			}
			final ArrayList<BattleCard> deck = new ArrayList<BattleCard>(member.getDeck());
			if (deck.size() > 0) {
				final BattleCard card = deck.remove(random(deck.size()));
				card.setState(BattleCardState.HAND);
				repository().save(card);
			}
		}
	}

	@Transactional
	public void registerAction(final BattleCard card, final Position target) {
		final BattleMember member = card.getOwner();
		final Battle battle = member.getBattle();
		final BattleAction action = ReflectionUtils.newInstance(realActionClass, battle);
		action.setCard(card);
		action.setTarget(target);
		repository().save(action);

		member.setExhausted(true);
		repository().save(member);

		for (BattleMember battleMember : battle.getMembers()) {
			if (!battleMember.isExhausted()) {
				return;
			}
		}
		endRound(battle);
	}

	public List<BattleField> getBattleField(final Hero hero, final Battle battle) {
		final Map<Position, BattleField> battleField = new HashMap<Position, BattleField>();
		boolean upSide = false;
		for (BattleMember member : battle.getMembers()) {
			final boolean enemy = !member.getHero().equals(hero);
			if (!enemy && member.getPosition().getY() == 0) {
				upSide = true;
			}
			battleField.put(member.getPosition(), new BattleField(member, enemy));
			for (BattleSummon summon : member.getSummons()) {
				battleField.put(summon.getPosition(), new BattleField(summon, enemy));
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
		final BattleMember member = ReflectionUtils.newInstance(realMemberClass, battle, hero);
		member.setMentalPower(10);
		member.setPosition(position);
		member.setExhausted(false);
		repository().save(member);

		final List<BattleCard> deck = new ArrayList<BattleCard>();
		for (HeroCard heroCard : hero.getCards()) {
			final BattleCard card = ReflectionUtils.newInstance(realCardClass, member, heroCard);
			card.setState(BattleCardState.DECK);
			deck.add(card);
		}
		for (int i = 0; i < deck.size() && i < 3; i++) {
			final BattleCard card = deck.remove(random(deck.size()));
			card.setState(BattleCardState.HAND);
			repository().save(card);
		}
		for (BattleCard card : deck) {
			repository().save(card);
		}
	}

	private int random(final int size) {
		return new Random().nextInt(size);
	}

	private List<?> getTargets(final TargetType targetType, final List<BattleField> battleFields, final Position position) {
		final ArrayList<Object> targets = new ArrayList<Object>();
		if (targetType.isMassive()) {
			for (BattleField field : battleFields) {
				if (field.supports(targetType)) {
					if (field.hasHero()) {
						targets.add(field.getMember());
					} else if (field.hasSummon()) {
						targets.add(field.getSummon());
					} else {
						targets.add(field.getPosition());
					}
				}
			}
		} else {
			for (BattleField field : battleFields) {
				if (field.supports(targetType) && field.getPosition().equals(position)) {
					if (field.hasHero()) {
						targets.add(field.getMember());
					} else if (field.hasSummon()) {
						targets.add(field.getSummon());
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
