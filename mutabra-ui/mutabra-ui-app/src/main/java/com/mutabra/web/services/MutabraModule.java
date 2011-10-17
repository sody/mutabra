package com.mutabra.web.services;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.annotations.Custom;
import com.mutabra.web.internal.CustomValidationDecorator;
import com.mutabra.web.internal.EntityEncoderFactory;
import com.mutabra.web.internal.I18nPropertyConduitSource;
import com.mutabra.web.internal.TranslatorImpl;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.javascript.*;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SubModule(value = {DomainModule.class, ServicesModule.class, SecurityModule.class})
public class MutabraModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withMarker(Custom.class);
		binder.bind(Translator.class, TranslatorImpl.class);
	}

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
	}

	@Contribute(ValueEncoderSource.class)
	public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
											 final TypeCoercer typeCoercer,
											 final EntityRepository repository) {
		configuration.add(BaseEntity.class, new EntityEncoderFactory<Long>(typeCoercer, repository, Long.class));
	}

	@Decorate(serviceInterface = PropertyConduitSource.class)
	public PropertyConduitSource decoratePropertyConduitSource(final PropertyConduitSource conduitSource,
															   final Translator translator,
															   final StringInterner interner) {
		return new I18nPropertyConduitSource(conduitSource, translator, interner);
	}

	@Contribute(TypeCoercer.class)
	public void contributeTypeCoercer(final Configuration<CoercionTuple> configuration) {
		configuration.add(CoercionTuple.create(String.class, String[].class, new Coercion<String, String[]>() {
			public String[] coerce(String input) {
				return input != null ? input.split(",") : null;
			}
		}));
	}

	@Custom
	@Contribute(JavaScriptStack.class)
	public void contributeMutabraJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
		configuration.add("reset", new StackExtension(StackExtensionType.STYLESHEET, "context:css/reset.css"));
		configuration.add("fonts", new StackExtension(StackExtensionType.STYLESHEET, "context:css/fonts.css"));
		configuration.add("layout", new StackExtension(StackExtensionType.STYLESHEET, "context:css/layout.css"));

		configuration.add("jquery", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery-1.6.2.js"));
		configuration.add("jquery-ui", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery-ui-1.8.16.js"));
		configuration.add("mutabra", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra.js"));
	}

	@Contribute(JavaScriptStackSource.class)
	public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
												@Custom final JavaScriptStack mutabraStack) {
		configuration.add("mutabra", mutabraStack);
	}

	@Contribute(MarkupRenderer.class)
	public void contributeMarkupRenderer(final OrderedConfiguration<MarkupRendererFilter> configuration,
										 final Environment environment) {
		final MarkupRendererFilter validationDecorator = new MarkupRendererFilter() {
			public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
				environment.push(ValidationDecorator.class, new CustomValidationDecorator(environment, writer));
				renderer.renderMarkup(writer);
				environment.pop(ValidationDecorator.class);
			}
		};
		configuration.add("CustomValidationDecorator", validationDecorator, "after:DefaultValidationDecorator");
	}

	@Contribute(PartialMarkupRenderer.class)
	public void contributePartialMarkupRenderer(final OrderedConfiguration<PartialMarkupRendererFilter> configuration,
												final Environment environment) {
		final PartialMarkupRendererFilter validationDecorator = new PartialMarkupRendererFilter() {
			public void renderMarkup(MarkupWriter writer, JSONObject reply, PartialMarkupRenderer renderer) {
				environment.push(ValidationDecorator.class, new CustomValidationDecorator(environment, writer));
				renderer.renderMarkup(writer, reply);
				environment.pop(ValidationDecorator.class);
			}
		};
		configuration.add("CustomValidationDecorator", validationDecorator, "after:DefaultValidationDecorator");
	}
}
