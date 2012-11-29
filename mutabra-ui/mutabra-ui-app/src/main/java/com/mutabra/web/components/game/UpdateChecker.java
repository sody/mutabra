package com.mutabra.web.components.game;

import com.mutabra.domain.game.Hero;
import com.mutabra.web.base.components.AbstractComponent;
import com.mutabra.web.services.AccountContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UpdateChecker extends AbstractComponent {

    @Inject
    private AccountContext accountContext;

    @Inject
    private JavaScriptSupport support;

    @Inject
    private ValueEncoderSource encoderSource;

    @Inject
    private PageRenderLinkSource linkSource;

    @AfterRender
    void renderScript() {
        final ValueEncoder<Hero> encoder = encoderSource.getValueEncoder(Hero.class);
        final String heroId = encoder.toClient(accountContext.getHero());
        final Link link = linkSource.createPageRenderLink(getResources().getPageName());

        support.addInitializerCall("updateChecker", new JSONObject()
                .put("hero_id", heroId)
                .put("url", link.toAbsoluteURI())
        );
    }
}
