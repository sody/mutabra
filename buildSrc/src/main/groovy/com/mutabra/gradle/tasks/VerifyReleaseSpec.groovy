/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

/**
 * @author Ivan Khalopik
 */
class VerifyReleaseSpec implements Configurable<VerifyReleaseSpec> {

    String requireBranch = 'master'
    boolean failOnCommitNeeded = true
    boolean failOnUnversionedFiles = false
    boolean failOnPublishNeeded = false
    boolean failOnUpdateNeeded = true
    boolean failOnSnapshotDependencies = true

    @Override
    VerifyReleaseSpec configure(final Closure cl) {
        return ConfigureUtil.configure(cl, this, false)
    }

    VerifyReleaseSpec requireBranch(String requireBranch) {
        this.requireBranch = requireBranch
        return this
    }

    VerifyReleaseSpec failOnCommitNeeded(boolean failOnCommitNeeded) {
        this.failOnCommitNeeded = failOnCommitNeeded
        return this
    }

    VerifyReleaseSpec failOnUnversionedFiles(boolean failOnUnversionedFiles) {
        this.failOnUnversionedFiles = failOnUnversionedFiles
        return this
    }

    VerifyReleaseSpec failOnPublishNeeded(boolean failOnPublishNeeded) {
        this.failOnPublishNeeded = failOnPublishNeeded
        return this
    }

    VerifyReleaseSpec failOnUpdateNeeded(boolean failOnUpdateNeeded) {
        this.failOnUpdateNeeded = failOnUpdateNeeded
        return this
    }
}
