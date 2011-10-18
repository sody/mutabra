package com.mutabra.web.components;

import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Role {

	@Parameter
	private boolean hero;

	@Parameter
	private boolean account;

	@Parameter
	private boolean anonymous;

	@Parameter(name = "else")
	private Block elseBlock;

	@Inject
	private AccountContext accountContext;

	@SuppressWarnings({"RedundantIfStatement"})
	boolean setupRender() {
		if (accountContext.getHero() != null) {
			return hero;
		}
		if (accountContext.getAccount() != null) {
			return account;
		}
		return anonymous;
	}
}
