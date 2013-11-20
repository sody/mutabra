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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Ivan Khalopik
 */
public class Release extends GradleBuild {
    private static Logger logger = LoggerFactory.getLogger(Release.class);

    private final VerifyReleaseSpec verify = new VerifyReleaseSpec();

    public Release(final StartParameter currentBuild, final GradleLauncherFactory gradleLauncherFactory) {
        super(currentBuild, gradleLauncherFactory);

        final StartParameter startParameter = getProject().getGradle().getStartParameter().newInstance();
        startParameter.setRecompileScripts(false);
        startParameter.setRerunTasks(false);
        setStartParameter(startParameter);

        tasks = [
                'verifyRelease',
                'build'
        ]

        project.task(
                'verifyRelease',
                group: 'release',
                description: 'Verifies release.'
        ) << this.&verifyRelease
    }

    public void verify(final Closure closure) {
        ConfigureUtil.configure(closure, this.verify);
    }


    protected void verifyRelease() {
        final Scm scm = scm();
        if (scm == null) {
            fail('Unsupported SCM system.')
        }

        final Scm.Status status = scm.status();
        if (verify.requireBranch != null && !verify.requireBranch.equals(status.currentBranch())) {
            fail("Current SCM branch is \"${status.currentBranch()}\" but \"${verify.requireBranch}\" is required.")
        }
        if (status.uncommitted() != null && !status.uncommitted().isEmpty()) {
            failOn(verify.failOnCommitNeeded, ([
                    'You have uncommitted files:',
                    '---------------------------',
                    * status.uncommitted(),
                    '---------------------------'
            ] as String[]).join('\n'))
        }
        if (status.unversioned() != null && !status.unversioned().isEmpty()) {
            failOn(verify.failOnUnversionedFiles, ([
                    'You have unversioned files:',
                    '---------------------------',
                    * status.unversioned(),
                    '---------------------------'
            ] as String[]).join('\n'))
        }
        if (status.ahead() > 0) {
            failOn(verify.failOnPublishNeeded, "You have ${status.ahead()} local change(s) to push.")
        }
        if (status.behind() > 0) {
            failOn(verify.failOnUpdateNeeded, "You have ${status.behind()} remote change(s) to pull.")
        }
    }

    protected Scm scm() {
        return new GitScm(project.rootProject.projectDir);
    }

    protected void failOn(final boolean condition, final String message) {
        if (condition) {
            fail(message);
        } else {
            logger.warn(message);
        }
    }

    protected void fail(final String message) {
        throw new GradleException(message);
    }
}
