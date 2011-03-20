package com.noname.services.common;

import com.mutabra.domain.common.Level;
import com.noname.services.CodedEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelFilter extends CodedEntityFilter<Level> {

	Long getRating();

}
