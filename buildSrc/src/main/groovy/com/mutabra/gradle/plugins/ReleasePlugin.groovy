/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import com.mutabra.gradle.tasks.ReleaseBuild
import com.mutabra.gradle.tasks.ReleaseFinish
import com.mutabra.gradle.tasks.ReleasePrepare

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild

/**
 * @author Ivan Khalopik
 */
class ReleasePlugin implements Plugin<Project> {
    static final String RELEASE_GROUP = 'release'
    static final String RELEASE_TASK_NAME = 'release'
    static final String PREPARE_RELEASE_TASK_NAME = 'prepareRelease'
    static final String VERIFY_RELEASE_TASK_NAME = 'verifyRelease'
    static final String PERFORM_RELEASE_TASK_NAME = 'performRelease'
    static final String FINISH_RELEASE_TASK_NAME = 'finishRelease'

    @Override
    void apply(final Project target) {
        target.plugins.apply(ScmPlugin)

        def convention = new ReleasePluginConvention(target)
        target.convention.plugins.put('release', convention)

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

        def verifyRelease = target.tasks.create(VERIFY_RELEASE_TASK_NAME, ReleaseBuild)
        verifyRelease.group = RELEASE_GROUP
        verifyRelease.description = 'Verifies release: build and run unit tests.'
        verifyRelease.startParameter.gradleUserHomeDir = convention.releaseHome
        verifyRelease.startParameter.recompileScripts = true
        verifyRelease.startParameter.rerunTasks = true
        verifyRelease.conventionMapping.map('tasks') {
            convention.release.verifyTasks
        }

        def performRelease = target.tasks.create(PERFORM_RELEASE_TASK_NAME, ReleaseBuild)
        performRelease.group = RELEASE_GROUP
        performRelease.description = 'Performs release: build and deploy.'
        performRelease.startParameter.gradleUserHomeDir = convention.releaseHome
        performRelease.startParameter.recompileScripts = false
        performRelease.startParameter.rerunTasks = false
        performRelease.conventionMapping.map('tasks') {
            convention.release.releaseTasks
        }

        def commitScm = target.tasks.create(FINISH_RELEASE_TASK_NAME, ReleaseFinish)
        commitScm.group = RELEASE_GROUP
        commitScm.description = 'Finishes release: commit changes in SCM, update version to the next snapshot.'
        commitScm.conventionMapping.map('source') {
            convention.release.update.source
        }
        commitScm.conventionMapping.map('nextVersion') {
            convention.nextVersion
        }
        commitScm.conventionMapping.map('tagName') {
            convention.tagName
        }

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
