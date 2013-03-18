package com.mutabra.services.battle;

import com.mutabra.domain.battle.Battle;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.domain.battle.Position;
import com.mutabra.domain.common.TargetType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattleField {
    private static final Position[] DUEL_POSITIONS = {
            new Position(0, 0),
            new Position(1, 0),
            new Position(2, 0)
    };

    private final Map<Side, Map<Position, Point>> points = new EnumMap<Side, Map<Position, Point>>(Side.class);
    private final Battle battle;
    private final BattleHero self;

    private BattleField(final Battle battle, final BattleHero self) {
        this.battle = battle;
        this.self = self;

        // fill positions with units
        for (BattleHero battleHero : battle.getHeroes()) {
            final Side side = battleHero.equals(self) ? Side.YOUR : Side.ENEMY;

            final Map<Position, Point> sidePoints = new HashMap<Position, Point>();
            sidePoints.put(battleHero.getPosition(), new Point(battleHero, side));
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                sidePoints.put(battleCreature.getPosition(), new Point(battleCreature, side));
            }
            points.put(side, sidePoints);
        }

        // fill empty positions
        for (Side side : Side.values()) {
            final Map<Position, Point> sidePoints = points.get(side);
            for (Position position : DUEL_POSITIONS) {
                if (!sidePoints.containsKey(position)) {
                    sidePoints.put(position, new Point(position, side));
                }
            }
        }
    }

    public Battle getBattle() {
        return battle;
    }

    public BattleHero getSelf() {
        return self;
    }

    public Point get(final Side side, final Position position) {
        return points.get(side).get(position);
    }

    public List<Point> get(final Side side) {
        return new ArrayList<Point>(points.get(side).values());
    }

    public List<Point> get(final TargetType targetType) {
        final List<Point> points = new ArrayList<Point>();
        for (Map<Position, Point> sidePoints : this.points.values()) {
            for (Point point : sidePoints.values()) {
                if (point.supports(targetType)) {
                    points.add(point);
                }
            }
        }
        return points;
    }

    public List<Point> get(final Position position, final TargetType targetType) {
        final List<Point> points = new ArrayList<Point>();
        if (targetType.isMassive()) {
            for (Map<Position, Point> sidePoints : this.points.values()) {
                for (Point point : sidePoints.values()) {
                    if (point.supports(targetType)) {
                        points.add(point);
                    }
                }
            }
        } else {
            for (Map<Position, Point> sidePoints : this.points.values()) {
                for (Point point : sidePoints.values()) {
                    if (point.supports(targetType) && point.getPosition().equals(position)) {
                        points.add(point);
                        return points;
                    }
                }
            }
        }
        return points;
    }

    public static BattleField create(final Battle battle, final BattleHero self) {
        return new BattleField(battle, self);
    }

    public enum Side {
        YOUR,
        ENEMY
    }

    public class Point {
        private final BattleCreature creature;
        private final BattleHero hero;

        private final Position position;
        private final Side side;

        public Point(final Position position, final Side side) {
            this.position = position;
            this.side = side;

            creature = null;
            hero = null;
        }

        public Point(final BattleHero hero, final Side side) {
            this.position = hero.getPosition();
            this.side = side;
            this.hero = hero;

            creature = null;
        }

        public Point(final BattleCreature creature, final Side side) {
            this.position = creature.getPosition();
            this.side = side;
            this.creature = creature;

            hero = null;
        }

        public Position getPosition() {
            return position;
        }

        public BattleCreature getCreature() {
            return creature;
        }

        public BattleHero getHero() {
            return hero;
        }

        public boolean hasHero() {
            return hero != null;
        }

        public boolean hasCreature() {
            return creature != null;
        }

        public boolean hasUnit() {
            return hero != null || creature != null;
        }

        public boolean isEnemySide() {
            return side == Side.ENEMY;
        }

        @SuppressWarnings({"RedundantIfStatement"})
        public boolean supports(final TargetType targetType) {
            if (isEnemySide() && !targetType.supportsEnemy()) {
                return false;
            }
            if (!isEnemySide() && !targetType.supportsFriend()) {
                return false;
            }
            if (hasHero() && !targetType.supportsHero()) {
                return false;
            }
            if (hasCreature() && !targetType.supportsCreature()) {
                return false;
            }
            if (!hasUnit() && !targetType.supportsEmpty()) {
                return false;
            }
            return true;
        }
    }
}
