package com.noname.web.pages;

import com.noname.web.base.pages.AbstractPage;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 */
public class Index extends AbstractPage {

	@Inject
	@Path("gomer.jpg")
	private Asset logo;

	public Asset getLogo() {
		return logo;
	}
}
