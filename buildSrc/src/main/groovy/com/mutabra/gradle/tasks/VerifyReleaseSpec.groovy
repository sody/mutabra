/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

/**
 * @author Ivan Khalopik
 */
class VerifyReleaseSpec {

    String requireBranch = 'master'
    boolean failOnCommitNeeded = true
    boolean failOnUnversionedFiles = true
    boolean failOnPublishNeeded = true
    boolean failOnUpdateNeeded = true
    boolean failOnSnapshotDependencies = true

    void requireBranch(String requireBranch) {
        this.requireBranch = requireBranch
    }

    void failOnCommitNeeded(boolean failOnCommitNeeded) {
        this.failOnCommitNeeded = failOnCommitNeeded
    }

    void failOnUnversionedFiles(boolean failOnUnversionedFiles) {
        this.failOnUnversionedFiles = failOnUnversionedFiles
    }

    void failOnPublishNeeded(boolean failOnPublishNeeded) {
        this.failOnPublishNeeded = failOnPublishNeeded
    }

    void failOnUpdateNeeded(boolean failOnUpdateNeeded) {
        this.failOnUpdateNeeded = failOnUpdateNeeded
    }
}
