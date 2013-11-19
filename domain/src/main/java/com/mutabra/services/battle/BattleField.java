/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.services.battle;

import com.mutabra.domain.battle.*;
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
    private static final BattlePosition[] DUEL_POSITIONS = {
            new BattlePosition(0, 0),
            new BattlePosition(1, 0),
            new BattlePosition(2, 0)
    };

    private final Map<BattleSide, Map<BattlePosition, Point>> points = new EnumMap<BattleSide, Map<BattlePosition, Point>>(BattleSide.class);
    private final Battle battle;
    private final BattleHero self;

    private BattleField(final Battle battle, final BattleHero self) {
        this.battle = battle;
        this.self = self;

        // fill positions with units
        for (BattleHero battleHero : battle.getHeroes()) {
            final BattleSide side = battleHero.equals(self) ? BattleSide.YOUR : BattleSide.ENEMY;

            final Map<BattlePosition, Point> sidePoints = new HashMap<BattlePosition, Point>();
            sidePoints.put(battleHero.getPosition(), new Point(battleHero, side));
            for (BattleCreature battleCreature : battleHero.getCreatures()) {
                sidePoints.put(battleCreature.getPosition(), new Point(battleCreature, side));
            }
            points.put(side, sidePoints);
        }

        // fill empty positions
        for (BattleSide side : BattleSide.values()) {
            final Map<BattlePosition, Point> sidePoints = points.get(side);
            for (BattlePosition position : DUEL_POSITIONS) {
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

    public List<Point> get() {
        final List<Point> all = new ArrayList<Point>();
        for (BattleSide side : BattleSide.values()) {
            all.addAll(points.get(side).values());
        }
        return all;
    }

    public Point get(final BattleSide side, final BattlePosition position) {
        return points.get(side).get(position);
    }

    public List<Point> get(final BattleSide side) {
        return new ArrayList<Point>(points.get(side).values());
    }

    public List<Point> get(final TargetType targetType) {
        final List<Point> points = new ArrayList<Point>();
        for (Map<BattlePosition, Point> sidePoints : this.points.values()) {
            for (Point point : sidePoints.values()) {
                if (point.supports(targetType)) {
                    points.add(point);
                }
            }
        }
        return points;
    }

    public static BattleField create(final Battle battle, final BattleHero self) {
        return new BattleField(battle, self);
    }

    public class Point {
        private final BattleCreature creature;
        private final BattleHero hero;

        private final BattleSide side;
        private final BattlePosition position;

        public Point(final BattlePosition position, final BattleSide side) {
            this.position = position;
            this.side = side;

            creature = null;
            hero = null;
        }

        public Point(final BattleHero hero, final BattleSide side) {
            this.position = hero.getPosition();
            this.side = side;
            this.hero = hero;

            creature = null;
        }

        public Point(final BattleCreature creature, final BattleSide side) {
            this.position = creature.getPosition();
            this.side = side;
            this.creature = creature;

            hero = null;
        }

        public BattleSide getSide() {
            return side;
        }

        public BattlePosition getPosition() {
            return position;
        }

        public BattleUnit getUnit() {
            return hasHero() ? hero : creature;
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
            return side == BattleSide.ENEMY;
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
