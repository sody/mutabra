package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Race;
import com.mutabra.services.BaseEntityService;
import com.mutabra.services.common.RaceQuery;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.RaceDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Races extends AbstractPage {

	@InjectService("raceService")
	private BaseEntityService<Race, RaceQuery> raceService;

	@InjectComponent
	private RaceDialog entityDialog;

	@Property
	private Race row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Race>(raceService.query(), Race.class);
	}

	Object onAdd() {
		return entityDialog.show(raceService.create());
	}

	Object onEdit(final Race race) {
		return entityDialog.show(race);
	}

	void onDelete(final Race race) {
		raceService.delete(race);
	}

	void onSuccess() {
		raceService.saveOrUpdate(entityDialog.getValue());
	}
}
