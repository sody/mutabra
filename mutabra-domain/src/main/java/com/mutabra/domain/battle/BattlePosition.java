package com.mutabra.domain.battle;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BattlePosition {
    private int x;
    private int y;

    public BattlePosition() {
    }

    public BattlePosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public String getId() {
        return x + "_" + y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getX());
        bits ^= Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BattlePosition) {
            final BattlePosition p = (BattlePosition) obj;
            return (getX() == p.getX()) && (getY() == p.getY());
        }
        return super.equals(obj);
    }
}
