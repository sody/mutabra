package com.mutabra.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum TranslationType {
	NAME("name"),
	DESCRIPTION("name", "description");

	private final List<String> variants;

	TranslationType(final String... variants) {
		this.variants = Arrays.asList(variants);
	}

	public List<String> getVariants() {
		return variants;
	}
}