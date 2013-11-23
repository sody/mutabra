/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import com.mutabra.gradle.plugins.Scm
import org.gradle.api.GradleException
import org.gradle.api.internal.ConventionTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author Ivan Khalopik
 */
class ReleaseVerifyScm extends ConventionTask {

    String requireBranch
    boolean failOnCommitNeeded
    boolean failOnUnversionedFiles
    boolean failOnPublishNeeded
    boolean failOnUpdateNeeded

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

    @TaskAction
    void verify() {
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
