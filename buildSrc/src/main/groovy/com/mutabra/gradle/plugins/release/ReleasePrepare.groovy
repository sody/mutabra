/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.release

import com.mutabra.gradle.plugins.scm.Scm
import org.gradle.api.GradleException
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Ivan Khalopik
 */
public class ReleasePrepare extends SourceTask {

    String requireBranch
    boolean failOnCommitNeeded
    boolean failOnUnversionedFiles
    boolean failOnPublishNeeded
    boolean failOnUpdateNeeded
    boolean failOnSnapshotDependencies
    String releaseVersion

    ReleasePrepare() {
        // default values
        failOnCommitNeeded = true
        failOnUpdateNeeded = true
        failOnSnapshotDependencies = true
    }

    @Input
    String getRequireBranch() {
        return requireBranch
    }

    @Input
    boolean isFailOnCommitNeeded() {
        return failOnCommitNeeded
    }

    @Input
    boolean isFailOnUnversionedFiles() {
        return failOnUnversionedFiles
    }

    @Input
    boolean isFailOnPublishNeeded() {
        return failOnPublishNeeded
    }

    @Input
    boolean isFailOnUpdateNeeded() {
        return failOnUpdateNeeded
    }

    @Input
    boolean isFailOnSnapshotDependencies() {
        return failOnSnapshotDependencies
    }

    @Input
    String getReleaseVersion() {
        return releaseVersion
    }

    @TaskAction
    void prepare() {
        // check whether scm is configured
        def scm = project.extensions.findByType(Scm)
        if (scm == null) {
            fail('Unsupported SCM system.')
        }

        def status = scm.status()
        // check required branch if needed
        def requireBranch = getRequireBranch()
        if (requireBranch != null && !requireBranch.equals(status.currentBranch())) {
            fail("Current SCM branch is \"${status.currentBranch()}\" but \"${requireBranch}\" is required.")
        }
        // check uncommitted changes
        if (status.uncommitted()) {
            failOn(isFailOnCommitNeeded(), [
                    'You have uncommitted files:',
                    '---------------------------',
                    * status.uncommitted(),
                    '---------------------------'
            ].join('\n'))
        }
        // check unversioned changes
        if (status.unversioned()) {
            failOn(isFailOnUnversionedFiles(), [
                    'You have unversioned files:',
                    '---------------------------',
                    * status.unversioned(),
                    '---------------------------'
            ].join('\n'))
        }
        // check outcoming changes
        if (status.ahead() > 0) {
            failOn(isFailOnPublishNeeded(), "You have ${status.ahead()} local change(s) to push.")
        }
        // check incoming changes
        if (status.behind() > 0) {
            failOn(isFailOnUpdateNeeded(), "You have ${status.behind()} remote change(s) to pull.")
        }

        // resolve release version
        def oldVersion = project.version
        def releaseVersion = getReleaseVersion()
        def source = getSource()

        // update versions
        source.each {
            project.ant.replaceregexp(file: it, match: oldVersion, replace: releaseVersion)
        }
        project.allprojects*.version = releaseVersion

        // check snapshot dependencies
        def message = ''
        project.allprojects.each { project ->
            def snapshots = [] as Set
            project.configurations.each { cfg ->
                snapshots += cfg.dependencies?.matching { Dependency d ->
                    d.version?.contains('SNAPSHOT')
                }
            }
            if (snapshots) {
                message += "\n\t${project.name}:\n\t\t${ snapshots.join('\n\t\t') }"
            }
        }
        if (message) {
            failOn(isFailOnSnapshotDependencies(), "You have snapshot dependencies: ${message}")
        }
    }

    void failOn(boolean condition, String message) {
        if (condition) {
            fail(message)
        } else {
            logger.warn(message)
        }
    }

    static void fail(String message) {
        throw new GradleException(message)
    }
}
