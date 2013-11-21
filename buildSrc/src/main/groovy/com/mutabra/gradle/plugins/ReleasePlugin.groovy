/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import com.mutabra.gradle.tasks.Release
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Ivan Khalopik
 */
class ReleasePlugin implements Plugin<Project> {
    static final String RELEASE_TASK_NAME = "release"
    static final String RELEASE_GROUP = "release"

    @Override
    void apply(final Project target) {
        def release = target.tasks.create(RELEASE_TASK_NAME, Release.class)
        release.description = 'Releases new version of application.'
        release.group = RELEASE_GROUP
    }
}
