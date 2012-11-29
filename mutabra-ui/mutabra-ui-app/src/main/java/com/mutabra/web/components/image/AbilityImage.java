package com.mutabra.web.components.image;

import com.mutabra.domain.common.Ability;
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
public class AbilityImage extends AbstractImage {

    @Property
    @Parameter(required = true, allowNull = false)
    private Ability ability;

    @Parameter(value = "prop:ability:name")
    private String title;

    @Inject
    private ImageSource imageSource;

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected String getAlt() {
        return ability.getCode();
    }

    @Override
    protected Asset getAsset() {
        return imageSource.getAbilityImage(ability);
    }
}
