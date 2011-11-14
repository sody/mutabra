package com.mutabra.domain;

import com.googlecode.objectify.annotation.Unindexed;
import com.mutabra.db.Tables;
import org.greatage.util.LocaleUtils;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity(name = Tables.TRANSLATION)
public class TranslationImpl extends BaseEntityImpl implements Translation {

	private String type;

	private String code;

	private String variant;

	private String locale = "";

	@Transient
	private Locale localeValue;

	@Unindexed
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(final String variant) {
		this.variant = variant;
	}

	public Locale getLocale() {
		if (localeValue == null) {
			localeValue = LocaleUtils.parseLocale(locale);
		}
		return localeValue;
	}

	public void setLocale(final Locale locale) {
		localeValue = locale;
		this.locale = locale != null ? locale.toString() : null;
	}
}