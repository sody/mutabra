/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Ivan Khalopik
 */
public class ReleasePrepare extends SourceTask {

    String releaseVersion

    @Input
    String getReleaseVersion() {
        return releaseVersion
    }

    @TaskAction
    void prepare() {
        // resolve release version
        def oldVersion = project.version
        def releaseVersion = getReleaseVersion()
        def source = getSource()

        // update versions
        source.each {
            project.ant.replaceregexp(file: it, match: oldVersion, replace: releaseVersion)
        }
        project.allprojects*.version = releaseVersion
    }

    static void fail(String message) {
        throw new GradleException(message)
    }
}
