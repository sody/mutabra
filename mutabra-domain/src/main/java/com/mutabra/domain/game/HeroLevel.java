package com.mutabra.domain.game;

import com.google.code.morphia.annotations.Embedded;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Embedded
public class HeroLevel {

    private String code;
    private long rating;
    private long next;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
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

    public void setNextLevelRating(final long rating) {
        next = rating;
    }
}
