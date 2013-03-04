package com.mutabra.domain.game;

import javax.persistence.Embeddable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embeddable
public class HeroLevelImpl implements HeroLevel {

    private String code;
    private long rating;
    private long next;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }

    public long getNextLevelRating() {
        return next;
    }

    public void setNextLevelRating(long rating) {
        next = rating;
    }
}
