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
    File releaseHome

    String releaseVersion
    String nextVersion
    String tagName

    @Nested
    VerifyReleaseSpec verify

    @Nested
    UpdateReleaseSpec update

    List<String> verifyTasks
    List<String> releaseTasks

    ReleaseSpec(final Project project) {
        this.project = project
        releaseHome = project.file("${project.rootDir}/.gradle/release")

        verify = new VerifyReleaseSpec()
        update = new UpdateReleaseSpec(project)
        verifyTasks = [BasePlugin.CLEAN_TASK_NAME, JavaBasePlugin.BUILD_TASK_NAME]
        releaseTasks = ['uploadArtifacts']
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

    String getReleaseVersion() {
        releaseVersion = releaseVersion ?:
            readValue('Enter release version', update.releaseVersion, update.automaticVersions)
        return releaseVersion
    }

    String getNextVersion() {
        nextVersion = nextVersion ?:
            readValue('Enter next version', update.nextVersion, update.automaticVersions)
        return nextVersion
    }

    String getTagName() {
        tagName = tagName ?:
            readValue('Enter tag name', update.tagName, update.automaticVersions)
        return tagName
    }

    static String readValue(String message, Object defaultValue, boolean silent) {
        def value = expand(defaultValue)
        return silent ? value : readValue(message, value)
    }

    static Object readValue(String message, Object defaultValue) {
        def value = expand(defaultValue)
        def msg = "\n?? ${message} ${ value ? '[' + value + ']' : ''}:"
        if (System.console()) {
            // read from java console if present
            return System.console().readLine(msg) ?: value
        }

        // read from system in stream
        println "${msg} (WAITING FOR INPUT BELOW)"
        return System.in.newReader().readLine() ?: value
    }

    static String expand(Object value) {
        return value instanceof Closure ? value.call() : value
    }
}
