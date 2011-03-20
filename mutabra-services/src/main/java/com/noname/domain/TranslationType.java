package com.noname.domain;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public enum TranslationType {
	STANDARD("description"),
	ABBREV("description", "abbrev");

	private final List<String> variants;

	TranslationType(String... variants) {
		this.variants = Arrays.asList(variants);
	}

	public List<String> getVariants() {
		return variants;
	}
}