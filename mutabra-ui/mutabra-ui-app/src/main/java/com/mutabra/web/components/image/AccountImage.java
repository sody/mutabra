package com.mutabra.web.components.image;

import com.mutabra.domain.security.Account;
import com.mutabra.web.base.components.AbstractImage;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccountImage extends AbstractImage {

	@Property
	@Parameter(required = true)
	private Account account;

	@Inject
	private ImageSource imageSource;

	@Override
	protected String getTitle() {
		return account != null ? account.getName() : "<anonymous>";
	}

	@Override
	protected String getAlt() {
		return account != null ? account.getName() : "<anonymous>";
	}

	@Override
	protected Asset getAsset() {
		return imageSource.getNotFoundImage(getSize());
	}
}
