/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.scm

import com.mutabra.gradle.plugins.scm.Scm.Status

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

    String exec(String... command)

    interface Status {

        String currentBranch()

        List<String> uncommitted()

        List<String> unversioned()

        int ahead()

        int behind()
    }
}