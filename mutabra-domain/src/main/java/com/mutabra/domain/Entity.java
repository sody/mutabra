package com.mutabra.domain;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 */
public interface Entity<PK extends Serializable> {

    PK getId();

    boolean isNew();
}
