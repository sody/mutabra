/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.release

import com.mutabra.gradle.plugins.scm.Scm
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Ivan Khalopik
 */
class ReleaseFinish extends SourceTask {

    String tagName
    String nextVersion

    @Input
    String getNextVersion() {
        return nextVersion
    }

    @Input
    String getTagName() {
        return tagName
    }

    @TaskAction
    void commit() {
        def scm = project.extensions.findByType(Scm)
        if (scm == null) {
            fail('Unsupported SCM system.')
        }

        def oldVersion = project.version
        def nextVersion = getNextVersion()
        def tagName = getTagName()
        def source = getSource()

        def commits = 0
        def tagAdded = false
        try {
            scm.tag("${tagName}", "[RELEASE]: Created tag ${tagName}")
            tagAdded = true

            // update versions to next snapshot
            source.each {
                project.ant.replaceregexp(file: it, match: oldVersion, replace: nextVersion)
            }
            project.allprojects*.version = nextVersion

            // commit changes
            scm.add(source.files)
            scm.commit("[RELEASE]: Project version updated to ${nextVersion}")
            commits++
        } catch (Exception e) {
            // delete tag if failed
            if (tagAdded) {
                try {
                    scm.removeTag(tagName)
                } catch (Exception ex) {
                    logger.error("Cannot delete tag ${tagName}.", ex)
                }
            }
            // rollback SCM changes if failed
            if (commits > 0) {
                try {
                    scm.rollback(commits)
                } catch (Exception ex) {
                    logger.error("Cannot rollback ${commits} commits.", ex)
                }
            }
            // rethrow
            throw e
        }
    }

    static void fail(String message) {
        throw new GradleException(message)
    }
}
