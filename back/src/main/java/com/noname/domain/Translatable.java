package com.noname.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Ivan Khalopik
 */
public interface Translatable extends Serializable {

	String getTranslationType();

	String getTranslationCode();

	Collection<String> getTranslationVariants();

}