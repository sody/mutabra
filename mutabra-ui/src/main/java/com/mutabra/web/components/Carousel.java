package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.internal.CSSConstants;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.MixinClasses;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.corelib.components.Hidden;
import org.apache.tapestry5.corelib.mixins.RenderInformals;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Carousel<T> extends AbstractComponent implements ClientElement {

    @Parameter(name = "id", value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Property
    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
    private String className;

    @Property
    @Parameter(required = true, allowNull = false)
    private Iterable<T> source;

    @Property
    @Parameter
    private T value;

    @Property
    @Parameter
    private ValueEncoder<T> encoder;

    @Property
    @Parameter
    private T row;

    @Property
    private int index;

    @Inject
    private JavaScriptSupport support;

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Environmental(false)
    private FormSupport formSupport;

    @InjectComponent
    private Hidden hiddenValue;

    @Component(inheritInformalParameters = true)
    @MixinClasses(RenderInformals.class)
    private Any container;

    private String assignedClientId;

    public String getClientId() {
        return assignedClientId;
    }

    public String getContainerClass() {
        return className != null ?
                className + " " + CSSConstants.CAROUSEL :
                CSSConstants.CAROUSEL;
    }

    public String getItemId() {
        return encoder.toClient(row);
    }

    public String getItemClass() {
        return row.equals(value) || (value == null && index == 0) ?
                CSSConstants.ACTIVE + " " + CSSConstants.CAROUSEL_ITEM :
                CSSConstants.CAROUSEL_ITEM;
    }

    public boolean isInsideForm() {
        return formSupport != null;
    }

    ValueEncoder defaultEncoder() {
        return defaultProvider.defaultValueEncoder("value", getResources());
    }

    @SetupRender
    void setupClientId() {
        assignedClientId = support.allocateClientId(clientId);
        if (value == null && source.iterator().hasNext()) {
            value = source.iterator().next();
        }
    }

    @AfterRender
    void renderScript() {
        if (isInsideForm()) {
            final JSONObject spec = new JSONObject(
                    "id", getClientId(),
                    "hiddenId", hiddenValue.getClientId());
            support.addInitializerCall("chooser", spec);
        }
    }
}
