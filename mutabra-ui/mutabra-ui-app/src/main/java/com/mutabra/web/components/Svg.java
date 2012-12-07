package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.services.ImageSource;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.parser.ComponentTemplate;
import org.apache.tapestry5.internal.parser.TemplateToken;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SupportsInformalParameters
public class Svg extends AbstractComponent {

    @Parameter(required = true, allowNull = false)
    private String image;

    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String type;

    @Parameter(value = "100")
    private int height;

    @Parameter(value = "100")
    private int width;

    @Inject
    private ImageSource imageSource;

    @BeginRender
    RenderCommand renderImage() {
        return new RenderCommand() {
            public void render(final MarkupWriter writer, final RenderQueue queue) {
                final ComponentTemplate template = imageSource.getImage(type, image);

                for (TemplateToken token : template.getTokens()) {
                    switch (token.getTokenType()) {
                        case DEFINE_NAMESPACE_PREFIX:
                        case DTD:
                        case START_ELEMENT:
                        case ATTRIBUTE:
                        case TEXT:
                        case CDATA:
                        case COMMENT:
                            ((RenderCommand) token).render(writer, queue);
                            break;
                        case END_ELEMENT:
                            final Element element = writer.getElement();
                            if ("g".equals(element.getName())) {
                                final Element container = element.getContainer();
                                final int widthValue = safeGet(container, "width");
                                final int heightValue = safeGet(container, "height");

                                if (widthValue > 0 && widthValue != width && heightValue > 0 && heightValue != height) {
                                    final double scaleX = (double) width / widthValue;
                                    final double scaleY = (double) height / heightValue;
                                    element.forceAttributes("transform", String.format(Locale.US, "scale(%.3f,%.3f)", scaleX, scaleY));
                                }
                            } else if ("svg".equals(element.getName())) {
                                getResources().renderInformalParameters(writer);
                                element.forceAttributes(
                                        "width", String.valueOf(width),
                                        "height", String.valueOf(height));
                            }
                            writer.end();
                            break;
                    }
                }
            }
        };
    }

    private int safeGet(final Element element, final String attribute) {
        final String value = element.getAttribute(attribute);
        try {
            return value != null ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
