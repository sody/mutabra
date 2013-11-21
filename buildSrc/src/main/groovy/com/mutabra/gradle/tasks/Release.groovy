/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.StartParameter
import org.gradle.api.GradleException
import org.gradle.api.tasks.GradleBuild
import org.gradle.initialization.GradleLauncherFactory
import org.gradle.util.ConfigureUtil

import javax.inject.Inject

/**
 * @author Ivan Khalopik
 */
public class Release extends GradleBuild {

    Scm scm
    VerifyReleaseSpec verify
    UpdateReleaseSpec update

    @Inject
    Release(StartParameter currentBuild, GradleLauncherFactory gradleLauncherFactory) {
        super(currentBuild, gradleLauncherFactory)

        startParameter.setRecompileScripts(false)
        startParameter.setRerunTasks(false)

        scm = findScm()
        verify = new VerifyReleaseSpec()
        update = new UpdateReleaseSpec(project)

        tasks = [
                'verifyScm',
                'prepareRelease',
                'build',
                'performRelease'
        ]

        project.task(
                'verifyScm',
                group: 'release',
                description: 'Verifies SCM state.'
        ) << this.&verifyScm

        project.task(
                'prepareRelease',
                group: 'release',
                description: 'Prepares release: updates version from snapshot to release.'
        ) << this.&prepareRelease

        project.task(
                'performRelease',
                group: 'release',
                description: 'Performs release: commits version update to SCM, tags it, updates it to the next snapshot.'
        ) << this.&performRelease
    }

    void verify(Closure closure) {
        ConfigureUtil.configure(closure, verify)
    }

    void update(Closure closure) {
        ConfigureUtil.configure(closure, update)
    }

    void verifyScm() {
        if (scm == null) {
            fail('Unsupported SCM system.')
        }

        def status = scm.status()
        if (verify.requireBranch != null && !verify.requireBranch.equals(status.currentBranch())) {
            fail("Current SCM branch is \"${status.currentBranch()}\" but \"${verify.requireBranch}\" is required.")
        }
        if (status.uncommitted()) {
            failOn(verify.failOnCommitNeeded, [
                    'You have uncommitted files:',
                    '---------------------------',
                    * status.uncommitted(),
                    '---------------------------'
            ].join('\n'))
        }
        if (status.unversioned()) {
            failOn(verify.failOnUnversionedFiles, [
                    'You have unversioned files:',
                    '---------------------------',
                    * status.unversioned(),
                    '---------------------------'
            ].join('\n'))
        }
        if (status.ahead() > 0) {
            failOn(verify.failOnPublishNeeded, "You have ${status.ahead()} local change(s) to push.")
        }
        if (status.behind() > 0) {
            failOn(verify.failOnUpdateNeeded, "You have ${status.behind()} remote change(s) to pull.")
        }
    }

    void prepareRelease() {
        def oldVersion = project.version
        def newVersion = update.version
        update.source.each {
            project.ant.replaceregexp(file: it, match: oldVersion, replace: newVersion)
        }
        update.projects*.version = newVersion
    }

    void performRelease() {
        def oldVersion = project.version
        def newVersion = update.nextVersion
        def tag = update.tag

        scm.add(update.source.files)
        scm.commit("[RELEASE]: Project version updated to ${oldVersion}")
        scm.tag("${tag}", "[RELEASE]: Created tag ${tag}")

        update.source.each {
            project.ant.replaceregexp(file: it, match: oldVersion, replace: newVersion)
        }
        update.projects*.version = newVersion

        scm.add(update.source.files)
        scm.commit("[RELEASE]: Project version updated to ${newVersion}")
    }

    Scm findScm() {
        return project.rootProject.file('.git').exists() ?
            new GitScm(project) :
            null
    }

    void failOn(boolean condition, String message) {
        if (condition) {
            fail(message)
        } else {
            logger.warn(message)
        }
    }

    void fail(String message) {
        throw new GradleException(message)
    }
}
