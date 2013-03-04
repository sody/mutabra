package com.mutabra.domain.common;

import com.googlecode.objectify.annotation.Indexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.LEVEL)
public class LevelImpl extends BaseEntityImpl implements Level {

    @Indexed
    private String code;

    private LevelType type;
    private long rating;

    public String getCode() {
        return code;
    }

    public LevelType getType() {
        return type;
    }

    public void setType(final LevelType type) {
        this.type = type;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }
}
