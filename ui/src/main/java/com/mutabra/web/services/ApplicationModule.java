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
import com.mutabra.web.internal.BattleEncoderFactory;
import com.mutabra.web.internal.ImageSourceImpl;
import com.mutabra.web.internal.MorphiaEncoderFactory;
import com.mutabra.web.internal.NamingObjectProvider;
import com.mutabra.web.internal.annotations.AuthMenuItem;
import org.apache.tapestry5.ComponentParameterConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.MasterObjectProvider;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.MetaDataLocator;
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
    public void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "ru,en");
        configuration.add(SymbolConstants.START_PAGE_NAME, "/");
        configuration.add(SymbolConstants.EXCEPTION_REPORT_PAGE, "error");
        configuration.add(SymbolConstants.FORM_CLIENT_LOGIC_ENABLED, "false");
        configuration.add(ComponentParameterConstants.ZONE_UPDATE_METHOD, "none");
        configuration.add(ComponentParameterConstants.ZONE_SHOW_METHOD, "none");

        configuration.add("mutabra.asset.root", "context:mutabra");
    }

    public static void bind(final ServiceBinder binder) {
        binder.bind(JavaScriptStack.class, ExtensibleJavaScriptStack.class).withId("MutabraJavaScriptStack");
        binder.bind(ImageSource.class, ImageSourceImpl.class);
    }

    @Contribute(MasterObjectProvider.class)
    public void contributeMasterObjectProvider(final OrderedConfiguration<ObjectProvider> configuration) {
        configuration.add("NamingObjectProvider", new NamingObjectProvider());
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
        configuration.add("bootstrap-carousel.js", new StackExtension(StackExtensionType.LIBRARY, "${mutabra.asset.root}/js/bootstrap/carousel.js"));

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
        configuration.add(AuthMenuItem.class, new MetaDataExtractor<AuthMenuItem>() {
            @Override
            public void extractMetaData(final MutableComponentModel model, final AuthMenuItem annotation) {
                model.setMeta("menu.item", annotation.value().name());
            }
        });
    }

    @Contribute(MetaDataLocator.class)
    public void contributeMetaDataLocator(final MappedConfiguration<String, String> configuration) {
        configuration.add("menu.item", "");
    }
}
