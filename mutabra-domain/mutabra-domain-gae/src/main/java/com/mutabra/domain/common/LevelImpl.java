package com.mutabra.domain.common;

import com.mutabra.db.Tables;
import com.mutabra.domain.CodedEntityImpl;
import com.mutabra.domain.TranslationType;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.LEVEL)
public class LevelImpl extends CodedEntityImpl implements Level {

    private long rating;

    public LevelImpl() {
        this(null);
    }

    public LevelImpl(final String code) {
        super(Tables.LEVEL, code, TranslationType.NAME);
    }

    public long getRating() {
        return rating;
    }

    public void setRating(final long rating) {
        this.rating = rating;
    }
}
