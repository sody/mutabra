/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.StartParameter
import org.gradle.api.tasks.GradleBuild
import org.gradle.api.tasks.Input
import org.gradle.initialization.GradleLauncherFactory

import javax.inject.Inject

/**
 * @author Ivan Khalopik
 */
class ReleaseBuild extends GradleBuild {

    List<String> tasks = new ArrayList<String>()

    @Inject
    ReleaseBuild(final StartParameter currentBuild, final GradleLauncherFactory gradleLauncherFactory) {
        super(currentBuild, gradleLauncherFactory)
    }

    @Input
    List<String> getTasks() {
        return tasks
    }

    void setTasks(List<String> tasks) {
        this.tasks = tasks
    }

    @Override
    StartParameter getStartParameter() {
        def startParameter = super.getStartParameter()
        // reassign tasks for build
        startParameter.setTaskNames(getTasks())
        return startParameter
    }
}
