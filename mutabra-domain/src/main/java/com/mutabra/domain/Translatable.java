package com.mutabra.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Translatable {
    String NAME = "name";
    String DESCRIPTION = "description";

    String getBasename();

    String getCode();
}
