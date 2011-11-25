package com.mutabra.services.game;

import com.mutabra.domain.game.Battle;
import com.mutabra.domain.game.BattleCard;
import com.mutabra.domain.game.BattleMember;
import com.mutabra.domain.game.BattlePlace;
import com.mutabra.domain.game.BattleSummon;
import com.mutabra.domain.game.Hero;
import com.mutabra.domain.game.HeroCard;
import com.mutabra.domain.game.Position;
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

	private Class<? extends BattleMember> realMemberClass;
	private Class<? extends BattleCard> realCardClass;

	public BattleServiceImpl(final EntityRepository repository) {
		super(repository, Battle.class);

		realMemberClass = repository.create(BattleMember.class).getClass();
		realCardClass = repository.create(BattleCard.class).getClass();
	}

	@Transactional
	public Battle createBattle(final Hero hero1, final Hero hero2) {
		final Battle battle = create();
		battle.setStartedAt(new Date());
		save(battle);

		addMember(battle, hero1, 1, 2);
		addMember(battle, hero2, 1, 0);

		hero1.setBattle(battle);
		hero2.setBattle(battle);
		repository().save(hero1);
		repository().save(hero2);

		return battle;
	}

	@Transactional
	public Battle startRound(final Battle battle) {
		if (battle.getRound() == 0) {
			for (BattleMember member : battle.getMembers()) {
				final ArrayList<BattleCard> deck = new ArrayList<BattleCard>(member.getDeck());
				for (int i = 0; i < deck.size() && i < 3; i++) {
					final BattleCard card = deck.remove(random(deck.size()));
					card.setInHand(true);
					repository().save(card);
				}
			}
			battle.setRound(1);
			save(battle);
		}
		//todo: for implementation
		return null;
	}

	public List<BattlePlace> getBattleField(final Hero hero, final Battle battle) {
		final Map<Position, BattlePlace> battleField = new HashMap<Position, BattlePlace>();
		boolean upSide = false;
		for (BattleMember member : battle.getMembers()) {
			final boolean enemy = !member.getHero().equals(hero);
			if (!enemy && member.getPosition().getY() == 0) {
				upSide = true;
			}
			battleField.put(member.getPosition(), new BattlePlace(member, enemy));
			for (BattleSummon summon : member.getSummons()) {
				battleField.put(summon.getPosition(), new BattlePlace(summon, enemy));
			}
		}

		for (Position position : DUEL_POSITIONS) {
			if (!battleField.containsKey(position)) {
				final boolean enemy = (position.getY() == 0 && !upSide) || (position.getY() > 0 && upSide);
				battleField.put(position, new BattlePlace(position, enemy));
			}
		}

		return new ArrayList<BattlePlace>(battleField.values());
	}

	private int random(final int size) {
		return new Random().nextInt(size);
	}

	private void addMember(final Battle battle, final Hero hero, final int x, final int y) {
		final BattleMember member = ReflectionUtils.newInstance(realMemberClass, battle, hero);
		member.getPosition().setX(x);
		member.getPosition().setY(y);
		repository().save(member);
		for (HeroCard heroCard : hero.getCards()) {
			addCard(member, heroCard);
		}
	}

	private void addCard(final BattleMember member, final HeroCard heroCard) {
		final BattleCard card = ReflectionUtils.newInstance(realCardClass, member, heroCard);
		repository().save(card);
	}
}
