/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */



package com.mutabra.gradle.plugins

import org.gradle.api.Project

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ReleasePluginConvention {

    Project project

    ReleaseSpec release

    File releaseHome
    String releaseVersion
    String nextVersion
    String tagName

    ReleasePluginConvention(Project project) {
        this.project = project

        releaseHome = project.file("${project.rootDir}/.gradle/release")

        release = new ReleaseSpec(project)
    }

    void release(Closure closure) {
        release.configure(closure)
    }

    String getReleaseVersion() {
        releaseVersion = releaseVersion ?:
            readValue('Enter release version', release.update.releaseVersion, release.update.automaticVersions)
        return releaseVersion
    }

    String getNextVersion() {
        nextVersion = nextVersion ?:
            readValue('Enter next version', release.update.nextVersion, release.update.automaticVersions)
        return nextVersion
    }

    String getTagName() {
        tagName = tagName ?:
            readValue('Enter tag name', release.update.tagName, release.update.automaticVersions)
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
