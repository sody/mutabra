package com.mutabra.web.services;

import com.mutabra.domain.BaseEntity;
import com.mutabra.web.internal.CustomValidationDecoratorFactory;
import com.mutabra.web.internal.EntityEncoderFactory;
import com.mutabra.web.internal.I18nPropertyConduitSource;
import com.mutabra.web.internal.LinkManagerImpl;
import com.mutabra.web.internal.TranslatorImpl;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.ValidationDecoratorFactory;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.ExtensibleJavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.greatage.domain.EntityRepository;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@SubModule(value = {DomainModule.class, ServicesModule.class, SecurityModule.class})
public class MutabraModule {

	public static void bind(final ServiceBinder binder) {
		binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withSimpleId();
		binder.bind(ValidationDecoratorFactory.class, CustomValidationDecoratorFactory.class).withSimpleId();
		binder.bind(LinkManager.class, LinkManagerImpl.class);
		binder.bind(Translator.class, TranslatorImpl.class);
	}

	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
	}

	@Contribute(ServiceOverride.class)
	public static void contributeServiceOverrides(final MappedConfiguration<Class, Object> configuration,
												  final @Local ValidationDecoratorFactory decoratorFactory) {
		configuration.add(ValidationDecoratorFactory.class, decoratorFactory);
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

	@Local
	@Contribute(JavaScriptStack.class)
	public void contributeJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
		configuration.add("reset", new StackExtension(StackExtensionType.STYLESHEET, "context:css/reset.css"));
		configuration.add("fonts", new StackExtension(StackExtensionType.STYLESHEET, "context:css/fonts.css"));
		configuration.add("layout", new StackExtension(StackExtensionType.STYLESHEET, "context:css/layout.css"));

		configuration.add("jquery", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery-1.6.2.js"));
		configuration.add("jquery-ui", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery-ui-1.8.16.js"));
		configuration.add("mutabra", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra.js"));
	}

	@Contribute(JavaScriptStackSource.class)
	public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
												final @Local JavaScriptStack stack) {
		configuration.add("mutabra", stack);
	}
}
