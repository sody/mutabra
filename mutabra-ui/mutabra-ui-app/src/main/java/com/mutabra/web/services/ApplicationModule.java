package com.mutabra.web.services;

import com.mutabra.web.internal.I18nPropertyConduitSource;
import com.mutabra.web.internal.ImageSourceImpl;
import com.mutabra.web.internal.TranslatorImpl;
import com.mutabra.web.internal.UpdateCheckerFilter;
import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.StringInterner;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Decorate;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.javascript.ExtensibleJavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;
import org.greatage.domain.internal.SessionManager;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationModule {

	@ApplicationDefaults
	@Contribute(SymbolProvider.class)
	public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
		configuration.add(SymbolConstants.START_PAGE_NAME, "/");
		configuration.add(SymbolConstants.EXCEPTION_REPORT_PAGE, "error");
		configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, "false");
		configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "none");
		configuration.add(ComponentParameterConstants.ZONE_SHOW_METHOD, "none");
	}

	public static void bind(final ServiceBinder binder) {
		binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withSimpleId();
//		binder.bind(ValidationDecoratorFactory.class, CustomValidationDecoratorFactory.class).withSimpleId();
		binder.bind(Translator.class, TranslatorImpl.class);
		binder.bind(ImageSource.class, ImageSourceImpl.class);
	}

/*
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
*/

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
				return input != null && !input.isEmpty() ? input.split(",") : null;
			}
		}));
	}

	@Contribute(ComponentMessagesSource.class)
	public void contributeComponentMessagesSource(@Value("context:WEB-INF/mail") final Resource mailMessages,
												  final OrderedConfiguration<Resource> configuration) {
		configuration.add("mail", mailMessages);
	}

	@Local
	@Contribute(JavaScriptStack.class)
	public void contributeJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
		configuration.add("mutabra-css", new StackExtension(StackExtensionType.STYLESHEET, "context:css/mutabra.css"));

		// jquery library
		configuration.add("jquery", new StackExtension(StackExtensionType.LIBRARY, "context:js/jquery/jquery-1.7.1.js"));

		// bootstrap plugins
		configuration.add("bootstrap-transition", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-transition.js"));
		configuration.add("bootstrap-dropdown", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-dropdown.js"));
		configuration.add("bootstrap-tab", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-tab.js"));
		configuration.add("bootstrap-alert", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-alert.js"));
		configuration.add("bootstrap-modal", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-modal.js"));
		configuration.add("bootstrap-carousel", new StackExtension(StackExtensionType.LIBRARY, "context:js/bootstrap/bootstrap-carousel.js"));

		// project plugins
		configuration.add("mutabra-battle", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra/mutabra-battle.js"));

		// project library for tapestry
		configuration.add("mutabra", new StackExtension(StackExtensionType.LIBRARY, "context:js/mutabra-init.js"));
	}

	@Contribute(JavaScriptStackSource.class)
	public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
												final @Local JavaScriptStack stack) {
		configuration.add("mutabra", stack);
	}

	@Contribute(RequestHandler.class)
	public void contributeRequestHandler(final OrderedConfiguration<RequestFilter> configuration,
										 final SessionManager<Object> executor) {
		configuration.add("RepositorySessionFilter", new RequestFilter() {
			public boolean service(final Request request, final Response response, final RequestHandler requestHandler) throws IOException {
				return executor.execute(new SessionManager.Callback<Boolean, Object>() {
					public Boolean doInSession(final Object session) throws Exception {
						return requestHandler.service(request, response);
					}
				});
			}
		});
		configuration.addInstance("UpdateCheckerFilter", UpdateCheckerFilter.class, "after:RepositorySessionFilter");
	}

}
