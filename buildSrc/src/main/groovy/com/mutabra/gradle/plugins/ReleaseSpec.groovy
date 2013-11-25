/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.Nested
import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

/**
 * @author Ivan Khalopik
 */
class ReleaseSpec implements Configurable<ReleaseSpec> {

    Project project

    @Nested
    VerifyReleaseSpec verify

    @Nested
    UpdateReleaseSpec update

    List<String> verifyTasks
    List<String> releaseTasks

    ReleaseSpec(final Project project) {
        this.project = project

        verify = new VerifyReleaseSpec()
        update = new UpdateReleaseSpec(project)
        verifyTasks = [ BasePlugin.CLEAN_TASK_NAME, JavaBasePlugin.BUILD_TASK_NAME ]
        releaseTasks = [ 'uploadArtifacts' ]
    }

    @Override
    ReleaseSpec configure(final Closure cl) {
        return ConfigureUtil.configure(cl, this, false)
    }

    ReleaseSpec verify(Closure closure) {
        verify.configure(closure)
        return this
    }

    ReleaseSpec update(Closure closure) {
        update.configure(closure)
        return this
    }

    ReleaseSpec verifyTasks(String... tasks) {
        verifyTasks = tasks as List
        return this
    }

    ReleaseSpec releaseTasks(String... tasks) {
        releaseTasks = tasks as List
        return this
    }
}
