package com.mutabra.domain;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodeUtils {

    public static String generateCode(final Enum<?> value) {
        return value.name().toLowerCase().replaceAll("_", "-");
    }
}
