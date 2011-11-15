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

	protected CodedEntityImpl(final String type, final TranslationType translationType) {
		this(type, null, translationType);
	}

	protected CodedEntityImpl(final String type, final String code, final TranslationType translationType) {
		this(type, code, translationType.getVariants());
	}

	protected CodedEntityImpl(final String type, final String code, final Collection<String> variants) {
		this.code = code;
		this.type = type.toUpperCase();
		this.variants = variants;
	}

	public String getCode() {
		return code;
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
