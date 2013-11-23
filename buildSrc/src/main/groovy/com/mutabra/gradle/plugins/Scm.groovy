/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import com.mutabra.gradle.plugins.Scm.Status

/**
 * @author Ivan Khalopik
 */
public interface Scm {

    Status status()

    void add(Object... source)

    void commit(String message)

    void reset(String commit)

    void tag(String tagName, String message)

    void deleteTag(String tagName)

    interface Status {

        String currentBranch()

        List<String> uncommitted()

        List<String> unversioned()

        int ahead()

        int behind()
    }
}