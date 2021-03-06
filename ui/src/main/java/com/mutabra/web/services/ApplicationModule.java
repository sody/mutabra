/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

import com.mutabra.domain.BaseEntity;
import com.mutabra.domain.CodedEntity;
import com.mutabra.domain.battle.BattleAbility;
import com.mutabra.domain.battle.BattleCard;
import com.mutabra.domain.battle.BattleCreature;
import com.mutabra.domain.battle.BattleHero;
import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.internal.BattleEncoderFactory;
import com.mutabra.web.internal.ImageSourceImpl;
import com.mutabra.web.internal.MenuModelSourceImpl;
import com.mutabra.web.internal.MorphiaEncoderFactory;
import com.mutabra.web.internal.annotations.AuthMenu;
import com.mutabra.web.internal.annotations.MainMenu;
import com.mutabra.web.internal.hack.EffectiveDocumentLinker;
import com.mutabra.web.internal.model.MenuModel;
import org.apache.tapestry5.BaseValidationDecorator;
import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.internal.services.DocumentLinker;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.ValidationDecoratorFactory;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.javascript.ExtensibleJavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.JavaScriptStackSource;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.apache.tapestry5.services.javascript.StackExtensionType;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationModule {

    @ApplicationDefaults
    @Contribute(SymbolProvider.class)
    public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration,
                                              @Symbol(SymbolConstants.PRODUCTION_MODE)
                                              final boolean productionMode) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
        configuration.add(SymbolConstants.START_PAGE_NAME, "/");
        configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, "false");
        configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "none");
        configuration.add(ComponentParameterConstants.ZONE_SHOW_METHOD, "none");

        configuration.add("mutabra.asset.root", "context:mutabra");

        if (productionMode) {
            configuration.add(SymbolConstants.EXCEPTION_REPORT_PAGE, "error");
        }
        configuration.add(ApplicationConstants.GOOGLE_ANALYTICS_DOMAIN, "mutabra.com");
        configuration.add(ApplicationConstants.GOOGLE_ANALYTICS_ACCOUNT, "UA-23122478-2");
        configuration.add(ApplicationConstants.COPYRIGHT_YEAR, "2013");
    }

    public static void bind(final ServiceBinder binder) {
        binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withId("MutabraJavaScriptStack");
        binder.bind(ImageSource.class, ImageSourceImpl.class);
        binder.bind(MenuModelSource.class, MenuModelSourceImpl.class);
    }

    @Contribute(ServiceOverride.class)
    public void contributeServiceOverrides(final MappedConfiguration<Class, Object> configuration) {
        // disable default validation decorator
        configuration.add(ValidationDecoratorFactory.class, new ValidationDecoratorFactory() {
            @Override
            public ValidationDecorator newInstance(final MarkupWriter writer) {
                return new BaseValidationDecorator();
            }
        });
    }

    @Contribute(TypeCoercer.class)
    public void contributeTypeCoercer(final Configuration<CoercionTuple> configuration) {
        configuration.add(CoercionTuple.create(String.class, String[].class, new Coercion<String, String[]>() {
            public String[] coerce(String input) {
                return input != null && !input.isEmpty() ? input.split(",") : null;
            }
        }));
        configuration.add(CoercionTuple.create(String.class, ObjectId.class, new Coercion<String, ObjectId>() {
            public ObjectId coerce(final String input) {
                return new ObjectId(input);
            }
        }));
        configuration.add(CoercionTuple.create(ObjectId.class, String.class, new Coercion<ObjectId, String>() {
            public String coerce(final ObjectId input) {
                return input.toString();
            }
        }));
    }

    @Contribute(ValueEncoderSource.class)
    public void contributeValueEncoderSource(final MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                             final TypeCoercer typeCoercer,
                                             final Datastore datastore,
                                             final AccountContext accountContext) {
        configuration.add(BaseEntity.class, new MorphiaEncoderFactory<ObjectId>(typeCoercer, datastore, ObjectId.class));
        configuration.add(CodedEntity.class, new MorphiaEncoderFactory<String>(typeCoercer, datastore, String.class));

        // configure value encoders for battle embedded objects
        final BattleEncoderFactory battleEncoderFactory = new BattleEncoderFactory(accountContext, typeCoercer);
        configuration.add(BattleHero.class, battleEncoderFactory);
        configuration.add(BattleCard.class, battleEncoderFactory);
        configuration.add(BattleCreature.class, battleEncoderFactory);
        configuration.add(BattleAbility.class, battleEncoderFactory);

    }

    @Contribute(ComponentMessagesSource.class)
    public void contributeComponentMessagesSource(final OrderedConfiguration<Resource> configuration,

                                                  @Value("context:WEB-INF/domain")
                                                  final Resource domainMessages) {
        configuration.add("domain", domainMessages);
    }

    @Local
    @Contribute(JavaScriptStack.class)
    public void setupMutabraJavaScriptStack(final OrderedConfiguration<StackExtension> configuration) {
        configuration.add("mutabra.css", new StackExtension(StackExtensionType.STYLESHEET, "${mutabra.asset.root}/css/mutabra.css"));

        // project library for tapestry
        configuration.add("mutabra-init.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/mutabra-init.js"));

        // jquery library
        configuration.add("jquery.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/jquery/jquery.js"));
        // bootstrap plugins
        configuration.add("bootstrap-transition.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/transition.js"));
        configuration.add("bootstrap-collapse.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/collapse.js"));
        configuration.add("bootstrap-dropdown.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/dropdown.js"));
        configuration.add("bootstrap-tab.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/tab.js"));
        configuration.add("bootstrap-alert.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/alert.js"));
        configuration.add("bootstrap-modal.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/modal.js"));
        configuration.add("bootstrap-tooltip.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/tooltip.js"));

        // project plugins
        configuration.add("mutabra-battle.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/mutabra-battle.js"));
    }

    @Contribute(JavaScriptStackSource.class)
    public void contributeJavaScriptStackSource(final MappedConfiguration<String, JavaScriptStack> configuration,
                                                final @Local JavaScriptStack stack) {
        configuration.add("mutabra", stack);
    }

    @Contribute(MetaWorker.class)
    public void contributeMetaWorker(final MappedConfiguration<Class, MetaDataExtractor> configuration) {
        configuration.add(AuthMenu.class, new MetaDataExtractor<AuthMenu>() {
            @Override
            public void extractMetaData(final MutableComponentModel model, final AuthMenu annotation) {
                model.setMeta(MenuModel.ACTIVE_KEY, annotation.value().name());
            }
        });
        configuration.add(MainMenu.class, new MetaDataExtractor<MainMenu>() {
            @Override
            public void extractMetaData(final MutableComponentModel model, final MainMenu annotation) {
                model.setMeta(MenuModel.ACTIVE_KEY, annotation.value().name());
            }
        });
    }

    @Contribute(MetaDataLocator.class)
    public void contributeMetaDataLocator(final MappedConfiguration<String, String> configuration) {
        configuration.add(MenuModel.ACTIVE_KEY, "");
    }

    @Contribute(MarkupRenderer.class)
    public void contributeMarkupRenderer(final OrderedConfiguration<MarkupRendererFilter> configuration,
                                         final Environment environment,

                                         @Symbol(SymbolConstants.OMIT_GENERATOR_META)
                                         final boolean omitGeneratorMeta,

                                         @Symbol(SymbolConstants.TAPESTRY_VERSION)
                                         final String tapestryVersion,

                                         @Symbol(SymbolConstants.COMPACT_JSON)
                                         final boolean compactJSON) {
        configuration.override("DocumentLinker", new MarkupRendererFilter() {
            public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
                final EffectiveDocumentLinker linker = new EffectiveDocumentLinker(omitGeneratorMeta, tapestryVersion, compactJSON);

                environment.push(DocumentLinker.class, linker);
                renderer.renderMarkup(writer);
                environment.pop(DocumentLinker.class);

                linker.updateDocument(writer.getDocument());
            }
        });
    }
}
