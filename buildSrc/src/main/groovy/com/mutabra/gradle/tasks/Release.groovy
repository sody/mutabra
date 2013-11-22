/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import com.mutabra.gradle.plugins.ReleasePlugin
import org.gradle.StartParameter
import org.gradle.api.GradleException
import org.gradle.api.tasks.GradleBuild
import org.gradle.api.tasks.Nested
import org.gradle.initialization.GradleLauncherFactory

import javax.inject.Inject

/**
 * @author Ivan Khalopik
 */
public class Release extends GradleBuild {

    Scm scm

    @Nested
    VerifyReleaseSpec verify

    @Nested
    UpdateReleaseSpec update

    String releaseVersion
    String nextVersion
    String tagName

    @Inject
    Release(StartParameter currentBuild, GradleLauncherFactory gradleLauncherFactory) {
        super(currentBuild, gradleLauncherFactory)

        startParameter.recompileScripts = false
        startParameter.rerunTasks = false

        scm = findScm()
        verify = new VerifyReleaseSpec()
        update = new UpdateReleaseSpec(project)

        tasks = [
                'verifyScm',
                'prepareRelease',
                'verifyRelease',
                'performRelease',
                'commitScm',
        ]

        project.task(
                'verifyScm',
                group: ReleasePlugin.RELEASE_GROUP,
                description: 'Verifies current SCM state.'
        ) << this.&verifyScm

        project.task(
                'prepareRelease',
                group: ReleasePlugin.RELEASE_GROUP,
                description: 'Prepares release: update versions from snapshot to release.'
        ) << this.&prepareRelease

        def releaseHome = project.file("${project.rootDir}/.gradle/release")

        project.task(
                'verifyRelease',
                type: GradleBuild,
                group: ReleasePlugin.RELEASE_GROUP,
                description: 'Verifies release: build and run unit tests.') {
            startParameter.gradleUserHomeDir = releaseHome
            startParameter.recompileScripts = true
            startParameter.rerunTasks = true
            tasks = ['clean', 'build']
        }

        project.task(
                'performRelease',
                type: GradleBuild,
                group: ReleasePlugin.RELEASE_GROUP,
                description: 'Performs release: build and deploy.') {
            startParameter.gradleUserHomeDir = releaseHome
            startParameter.recompileScripts = false
            startParameter.rerunTasks = false
            tasks = ['uploadArtifacts']
        }

        project.task(
                'commitScm',
                group: ReleasePlugin.RELEASE_GROUP,
                description: 'Commits changes in SCM: commit and tag release version, update it to the next snapshot.'
        ) << this.&commitScm
    }

    void verify(Closure closure) {
        verify.configure(closure)
    }

    void update(Closure closure) {
        update.configure(closure)
    }

    void verifyTasks(String... tasks) {
        project.verifyRelease.tasks = tasks as List
    }

    void releaseTasks(String... tasks) {
        project.performRelease.tasks = tasks as List
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
        // resolve versions
        def oldVersion = project.version
        releaseVersion = update.version
        nextVersion = update.nextVersion
        tagName = update.tag

        // confirm versions if not automatic
        if (!update.automaticVersions) {
            releaseVersion = readLine("Enter release version", releaseVersion)
            nextVersion = readLine("Enter next version", nextVersion)
            tagName = readLine("Enter tag name", tagName)
        }

        // update versions
        update.source.each {
            project.ant.replaceregexp(file: it, match: oldVersion, replace: releaseVersion)
        }
        update.projects*.version = releaseVersion
    }

    void commitScm() {
        def oldVersion = project.version
        // for incremental release only
        // for usual release all this values are already assigned
        if (nextVersion == null || tagName == null) {
            nextVersion = nextVersion ?: update.nextVersion
            tagName = tagName ?: update.tag

            if (!update.automaticVersions) {
                nextVersion = readLine("Enter next version", nextVersion)
                tagName = readLine("Enter tag name", tagName)
            }
        }

        def commits = 0
        def tagAdded = false
        try {
            // commit changes and tag release
            scm.add(update.source.files)
            scm.commit("[RELEASE]: Project version updated to ${oldVersion}")
            commits++
            scm.tag("${tagName}", "[RELEASE]: Created tag ${tagName}")
            tagAdded = true

            // update versions to next snapshot
            update.source.each {
                project.ant.replaceregexp(file: it, match: oldVersion, replace: nextVersion)
            }
            update.projects*.version = nextVersion

            // commit changes
            scm.add(update.source.files)
            scm.commit("[RELEASE]: Project version updated to ${nextVersion}")
            commits++
        } catch (Exception e) {
            // reset SCM changes if failed
            if (commits > 0) {
                scm.reset("HEAD~${commits}")
            }
            // delete tag if failed
            if (tagAdded) {
                scm.deleteTag(tagName)
            }
            // rethrow
            throw e
        }
    }

    Scm findScm() {
        return project.rootProject.file('.git').exists() ?
            new GitScm(project) :
            null
    }

    String readLine(String message, Object defaultValue = null) {
        String _message = "\n?? ${message}" + (defaultValue ? " [$defaultValue]:" : ":")
        if (System.console()) {
            return System.console().readLine(_message) ?: defaultValue
        }
        println "${_message} (WAITING FOR INPUT BELOW)"
        return System.in.newReader().readLine() ?: defaultValue
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
