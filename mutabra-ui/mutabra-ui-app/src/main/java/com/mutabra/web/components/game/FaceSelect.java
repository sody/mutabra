package com.mutabra.web.components.game;

import com.mutabra.domain.common.Face;
import com.mutabra.domain.common.Race;
import com.mutabra.services.CodedEntityService;
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
public class FaceSelect implements ClientElement {

	@Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String clientId;

	@Property
	@Parameter
	private Face value;

	@InjectService("faceService")
	private CodedEntityService<Face> faceService;

	@Property
	private Face row;

	@Inject
	private JavaScriptSupport support;

	@Cached
	public List<Face> getSource() {
		return faceService.query().list();
	}

	public String getClientId() {
		return clientId;
	}

	@AfterRender
	void renderScript() {
		support.addInitializerCall("carousel", getClientId());
	}
}
