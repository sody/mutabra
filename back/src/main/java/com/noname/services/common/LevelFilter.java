package com.noname.services.common;

import com.noname.domain.common.Level;
import com.noname.services.CodedEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelFilter extends CodedEntityFilter<Level> {

	Long getRating();

}
