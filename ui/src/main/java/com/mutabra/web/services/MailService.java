/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.web.services;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface MailService {

    void send(String to, String topic, String body);
}
