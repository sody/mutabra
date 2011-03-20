package com.mutabra.services.common;

import com.mutabra.domain.common.Level;
import com.mutabra.services.CodedEntityFilter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LevelFilter extends CodedEntityFilter<Level> {

	Long getRating();

}
