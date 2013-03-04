package com.mutabra.domain.common;

import com.googlecode.objectify.annotation.Indexed;
import com.mutabra.db.Tables;
import com.mutabra.domain.BaseEntityImpl;

import javax.persistence.Entity;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.FACE)
public class FaceImpl extends BaseEntityImpl implements Face {

    @Indexed
    private String code;

    public String getCode() {
        return code;
    }
}
