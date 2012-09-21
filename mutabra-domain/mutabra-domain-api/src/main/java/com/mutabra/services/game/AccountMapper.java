package com.mutabra.services.game;

import com.mutabra.domain.BaseEntity;
import com.mutabra.services.BaseEntityMapper;
import org.greatage.domain.PropertyMapper;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountMapper<E extends BaseEntity> extends BaseEntityMapper<E> {
	public final PropertyMapper<Long, E, String> email$ = property("email");
	public final PropertyMapper<Long, E, String> facebookUser$ = property("facebookUser");
	public final PropertyMapper<Long, E, String> twitterUser$ = property("twitterUser");
	public final PropertyMapper<Long, E, String> googleUser$ = property("googleUser");
	public final PropertyMapper<Long, E, String> vkUser$ = property("vkUser");

	public AccountMapper(final String path) {
		super(path);
	}
}
