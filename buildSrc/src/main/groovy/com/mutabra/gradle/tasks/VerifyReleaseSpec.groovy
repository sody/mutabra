/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

/**
 * @author Ivan Khalopik
 */
class VerifyReleaseSpec {

    String requireBranch = 'master';
    boolean failOnCommitNeeded = true;
    boolean failOnUnversionedFiles = true;
    boolean failOnPublishNeeded = true;
    boolean failOnUpdateNeeded = true;

    void requireBranch(final String requireBranch) {
        this.requireBranch = requireBranch
    }

    void failOnCommitNeeded(final boolean failOnCommitNeeded) {
        this.failOnCommitNeeded = failOnCommitNeeded
    }

    void failOnUnversionedFiles(final boolean failOnUnversionedFiles) {
        this.failOnUnversionedFiles = failOnUnversionedFiles
    }

    void failOnPublishNeeded(final boolean failOnPublishNeeded) {
        this.failOnPublishNeeded = failOnPublishNeeded
    }

    void failOnUpdateNeeded(final boolean failOnUpdateNeeded) {
        this.failOnUpdateNeeded = failOnUpdateNeeded
    }
}
