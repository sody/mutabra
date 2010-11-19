package com.noname.web.components.common;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.greatage.tapestry.internal.SelectModelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

//todo: change this by introducing global select model

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TimeZoneSelect {

	@Parameter(required = true)
	private TimeZone timeZone;

	@Inject
	private SelectModelBuilder selectModelBuilder;

	private SelectModel timeZoneModel;

	public SelectModel getTimeZoneModel() {
		if (timeZoneModel == null) {
			final List<TimeZone> timeZones = getTimeZones();
			timeZoneModel = selectModelBuilder.buildFormatted(TimeZone.class, timeZones, "%s", "displayName");
		}
		return timeZoneModel;
	}

	private List<TimeZone> getTimeZones() {
		final List<TimeZone> timeZones = new ArrayList<TimeZone>();
		for (String timeZoneId : TimeZone.getAvailableIDs()) {
			timeZones.add(TimeZone.getTimeZone(timeZoneId));
		}
		return timeZones;
	}
}
