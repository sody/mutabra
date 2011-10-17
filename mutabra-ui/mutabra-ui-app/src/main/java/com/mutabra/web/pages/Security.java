package com.mutabra.web.pages;

import com.mutabra.web.base.pages.AbstractPage;
import org.apache.tapestry5.annotations.Property;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Security extends AbstractPage {

	@Property
	private String email;

	@Property
	private String password;

}
