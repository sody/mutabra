/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import com.mutabra.gradle.tasks.Scm.Status

/**
 * @author Ivan Khalopik
 */
public interface Scm {

    Status status();

    interface Status {

        String currentBranch();

        List<String> uncommitted();

        List<String> unversioned();

        int ahead();

        int behind();
    }
}