package com.mutabra.domain;

import com.googlecode.objectify.annotation.Indexed;

import javax.persistence.Transient;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CodedEntityImpl extends BaseEntityImpl implements CodedEntity {

	@Indexed
	private String code;

	@Transient
	private final String type;

	@Transient
	private final Collection<String> variants;

	protected CodedEntityImpl(final String type, final Collection<String> variants) {
		this.type = type.toUpperCase();
		this.variants = variants;
	}

	protected CodedEntityImpl(final String type, final TranslationType translationType) {
		this(type, translationType.getVariants());
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getTranslationType() {
		return type;
	}

	public String getTranslationCode() {
		return code;
	}

	public Collection<String> getTranslationVariants() {
		return variants;
	}
}
