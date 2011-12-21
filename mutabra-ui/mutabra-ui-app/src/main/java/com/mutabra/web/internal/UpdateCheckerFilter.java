package com.mutabra.web.internal;

import com.mutabra.domain.game.Hero;
import com.mutabra.services.game.HeroService;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ValueEncoderSource;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UpdateCheckerFilter implements RequestFilter {
	private final HeroService heroService;
	private final ValueEncoder<Hero> encoder;
	private final String outputEncoding;
	private final boolean compactJSON;

	public UpdateCheckerFilter(final HeroService heroService, final ValueEncoderSource encoderSource,
							   @Symbol(SymbolConstants.CHARSET) final String outputEncoding,
							   @Symbol(SymbolConstants.COMPACT_JSON) final boolean compactJSON) {
		this.outputEncoding = outputEncoding;
		this.compactJSON = compactJSON;
		this.heroService = heroService;
		this.encoder = encoderSource.getValueEncoder(Hero.class);
	}

	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		final String heroId = request.getParameter("check_hero");
		if (heroId != null) {
			final Hero hero = encoder.toValue(heroId);
			if (hero != null) {
				final ContentType contentType = new ContentType(InternalConstants.JSON_MIME_TYPE, outputEncoding);
				final PrintWriter writer = response.getPrintWriter(contentType.toString());
				final JSONObject json = new JSONObject().put("ready", hero.isReady());
				json.print(writer, compactJSON);
				writer.close();
				if (hero.isReady()) {
					hero.setReady(false);
					heroService.saveOrUpdate(hero);
				}
				return true;
			}
		}
		return handler.service(request, response);
	}
}
