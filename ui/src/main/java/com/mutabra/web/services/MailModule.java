/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

import com.mutabra.web.ApplicationConstants;
import com.mutabra.web.internal.MailServiceImpl;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.messages.ComponentMessagesSource;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Ivan Khalopik
 */
public class MailModule {

    @FactoryDefaults
    @Contribute(SymbolProvider.class)
    public void contributeFactoryDefaults(final MappedConfiguration<String, String> configuration) {
        // support email
        configuration.add(ApplicationConstants.SUPPORT_EMAIL, "${env.support_email}");
        // robot email
        configuration.add(ApplicationConstants.ROBOT_EMAIL, "${env.robot_id}");
        configuration.add(ApplicationConstants.ROBOT_PASSWORD, "${env.robot_secret}");
    }

    @Contribute(ComponentMessagesSource.class)
    public void contributeComponentMessagesSource(final OrderedConfiguration<Resource> configuration,

                                                  @Value("context:WEB-INF/mail")
                                                  final Resource mailMessages) {
        configuration.add("mail", mailMessages);
    }

    public Session buildMailSession(@Symbol(ApplicationConstants.ROBOT_EMAIL)
                                    final String user,

                                    @Symbol(ApplicationConstants.ROBOT_PASSWORD)
                                    final String password,

                                    @Value("classpath:mail.properties")
                                    final Resource mailProperties) throws IOException {

        // mail configuration
        final Properties properties = new Properties();

        InputStreamReader reader = null;
        try {
            // read mail configuration from file
            reader = new InputStreamReader(mailProperties.openStream());
            properties.load(reader);

            // configure credentials
            properties.put("mail.user", user);
            properties.put("mail.password", password);
            // from should be the same as user
            properties.put("mail.from", user);
        } finally {
            InternalUtils.close(reader);
        }


        return password != null ?
                Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                }) :
                Session.getDefaultInstance(properties);
    }

    public MailService buildMailService(@Local final Session session) {
        return new MailServiceImpl(session);
    }
}
