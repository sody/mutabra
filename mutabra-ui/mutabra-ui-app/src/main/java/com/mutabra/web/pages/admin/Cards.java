package com.mutabra.web.pages.admin;

import com.mutabra.domain.common.Card;
import com.mutabra.services.CodedEntityService;
import com.mutabra.services.TranslationService;
import com.mutabra.web.base.pages.AbstractPage;
import com.mutabra.web.components.admin.CardDialog;
import com.mutabra.web.internal.BaseEntityDataSource;
import com.mutabra.web.services.Translator;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@RequiresAuthentication
@RequiresPermissions("card:view")
public class Cards extends AbstractPage {

	@InjectService("cardService")
	private CodedEntityService<Card> cardService;

	@Inject
	private TranslationService translationService;

	@Inject
	private Translator translator;

	@InjectComponent
	private CardDialog entityDialog;

	@Property
	private Card row;

	public GridDataSource getSource() {
		return new BaseEntityDataSource<Card>(cardService.query(), Card.class);
	}

	@OnEvent(value = "edit")
	Object editCard(final Card card) {
		return entityDialog.show(card);
	}

	@OnEvent(value = EventConstants.SUCCESS)
	@RequiresPermissions("card:edit")
	Object saveCard() {
		cardService.saveOrUpdate(entityDialog.getValue());
		translationService.saveTranslations(entityDialog.getTranslations());
		//todo: should be automatic
		translator.invalidateCache(entityDialog.getValue());
		return this;
	}
}
