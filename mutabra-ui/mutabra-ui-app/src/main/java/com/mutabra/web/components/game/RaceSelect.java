package com.mutabra.web.components.game;

import com.mutabra.domain.common.Race;
import com.mutabra.services.BaseEntityService;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class RaceSelect implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter
	private Race value;

	@InjectService("raceService")
	private BaseEntityService<Race> raceService;

	@Property
	private Race row;

	@Inject
	private JavaScriptSupport support;

	@Cached
	public List<Race> getSource() {
		return raceService.query().list();
	}

	public String getClientId() {
		return clientId;
	}

	@AfterRender
	void renderScript() {
		support.addInitializerCall("carousel", getClientId());
	}
}
