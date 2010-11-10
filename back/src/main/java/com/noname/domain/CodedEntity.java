package com.noname.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivan Khalopik
 */
@MappedSuperclass
public class CodedEntity extends BaseEntity implements Translatable {

	@Column(name = "CODE", nullable = false)
	private String code;

	@Transient
	private final String type;

	@Transient
	private final Collection<String> variants;

	@Transient
	private final Map<String, Translation> translations = new HashMap<String, Translation>();

	protected CodedEntity(final String type, final Collection<String> variants) {
		this.type = type;
		this.variants = variants;
	}

	protected CodedEntity(final String basename, final TranslationType translationType) {
		this(basename, translationType.getVariants());
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public Map<String, Translation> getTranslations() {
		return translations;
	}

	public void setTranslations(final Map<String, Translation> translations) {
		this.translations.clear();
		this.translations.putAll(translations);
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
