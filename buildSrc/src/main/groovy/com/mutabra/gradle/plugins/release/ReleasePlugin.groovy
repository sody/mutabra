/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.release

import com.mutabra.gradle.plugins.scm.ScmPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild

/**
 * @author Ivan Khalopik
 */
class ReleasePlugin implements Plugin<Project> {
    public static final String RELEASE_GROUP = 'release'
    public static final String RELEASE_TASK_NAME = 'release'
    public static final String PREPARE_RELEASE_TASK_NAME = 'prepareRelease'
    public static final String VERIFY_RELEASE_TASK_NAME = 'verifyRelease'
    public static final String PERFORM_RELEASE_TASK_NAME = 'performRelease'
    public static final String FINISH_RELEASE_TASK_NAME = 'finishRelease'

    @Override
    void apply(final Project target) {
        target.plugins.apply(ScmPlugin)

        def convention = new ReleasePluginConvention(target)
        target.convention.plugins.put('release', convention)

        addPrepareReleaseTask(target, convention)
        addVerifyReleaseTask(target, convention)
        addPerformReleaseTask(target, convention)
        addFinishReleaseTask(target, convention)

        addReleaseTask(target)
    }

    void addPrepareReleaseTask(Project target, ReleasePluginConvention convention) {
        def prepareRelease = target.tasks.create(PREPARE_RELEASE_TASK_NAME, ReleasePrepare)
        prepareRelease.group = RELEASE_GROUP
        prepareRelease.description = 'Prepares release: verify current SCM state, update versions from snapshot to release.'
        prepareRelease.conventionMapping.map('requireBranch') {
            convention.release.verify.requireBranch
        }
        prepareRelease.conventionMapping.map('failOnCommitNeeded') {
            convention.release.verify.failOnCommitNeeded
        }
        prepareRelease.conventionMapping.map('failOnUnversionedFiles') {
            convention.release.verify.failOnUnversionedFiles
        }
        prepareRelease.conventionMapping.map('failOnUpdateNeeded') {
            convention.release.verify.failOnUpdateNeeded
        }
        prepareRelease.conventionMapping.map('failOnPublishNeeded') {
            convention.release.verify.failOnPublishNeeded
        }
        prepareRelease.conventionMapping.map('source') {
            convention.release.update.source
        }
        prepareRelease.conventionMapping.map('releaseVersion') {
            convention.releaseVersion
        }
    }

    void addVerifyReleaseTask(Project target, ReleasePluginConvention convention) {
        def verifyRelease = target.tasks.create(VERIFY_RELEASE_TASK_NAME, ReleaseBuild)
        verifyRelease.group = RELEASE_GROUP
        verifyRelease.description = 'Verifies release: build and run unit tests.'
        verifyRelease.startParameter.gradleUserHomeDir = convention.releaseHome
        verifyRelease.startParameter.recompileScripts = true
        verifyRelease.startParameter.rerunTasks = true
        verifyRelease.conventionMapping.map('tasks') {
            convention.release.verifyTasks
        }
    }

    void addPerformReleaseTask(Project target, ReleasePluginConvention convention) {
        def performRelease = target.tasks.create(PERFORM_RELEASE_TASK_NAME, ReleaseBuild)
        performRelease.group = RELEASE_GROUP
        performRelease.description = 'Performs release: build and deploy.'
        performRelease.startParameter.gradleUserHomeDir = convention.releaseHome
        performRelease.startParameter.recompileScripts = false
        performRelease.startParameter.rerunTasks = false
        performRelease.conventionMapping.map('tasks') {
            convention.release.releaseTasks
        }
    }

    void addFinishReleaseTask(Project target, ReleasePluginConvention convention) {
        def finishRelease = target.tasks.create(FINISH_RELEASE_TASK_NAME, ReleaseFinish)
        finishRelease.group = RELEASE_GROUP
        finishRelease.description = 'Finishes release: commit changes in SCM, update version to the next snapshot.'
        finishRelease.conventionMapping.map('source') {
            convention.release.update.source
        }
        finishRelease.conventionMapping.map('nextVersion') {
            convention.nextVersion
        }
        finishRelease.conventionMapping.map('tagName') {
            convention.tagName
        }
    }

    void addReleaseTask(Project target) {
        def release = target.tasks.create(RELEASE_TASK_NAME, GradleBuild.class)
        release.group = RELEASE_GROUP
        release.description = 'Releases new version of application.'
        release.tasks = [
                PREPARE_RELEASE_TASK_NAME,
                VERIFY_RELEASE_TASK_NAME,
                PERFORM_RELEASE_TASK_NAME,
                FINISH_RELEASE_TASK_NAME
        ]
        release.startParameter.recompileScripts = false
        release.startParameter.rerunTasks = false
    }
}
