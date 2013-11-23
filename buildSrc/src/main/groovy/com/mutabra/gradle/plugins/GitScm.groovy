/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.slf4j.Logger

/**
 * @author Ivan Khalopik
 */
class GitScm implements Scm {
    static final String INFO = 'info'
    static final String UNVERSIONED = 'unversioned'
    static final String UNCOMMITTED = 'uncommitted'

    Project project

    File workTree
    File gitDir
    Logger logger

    GitScm(Project project) {
        this.project = project

        workTree = project.rootDir
        gitDir = project.rootProject.file('.git')
        logger = project.logger
    }

    @Override
    Scm.Status status() {
        // update remotes
        //TODO: fix this
        //exec('remote', 'update')

        // get status
        def result = exec('status', '-sb').readLines().groupBy {
            it ==~ /^\s*#{2}.*/ ?
                INFO :
                it ==~ /^\s*\?{2}.*/ ?
                    UNVERSIONED :
                    UNCOMMITTED
        }
        // process info: current branch, ahead and behind statuses
        def info = result[INFO][0]
        def currentBranch = (info =~ /^\s*\#{2}\s*([^\.{3}]*).*/)[0][1]
        def ahead = info ==~ /.*ahead (\d+).*/ ? Integer.parseInt((info =~ /.*ahead (\d+).*/)[0][1]) : 0
        def behind = info ==~ /.*behind (\d+).*/ ? Integer.parseInt((info =~ /.*behind (\d+).*/)[0][1]) : 0
        // process files info
        def uncommitted = result[UNCOMMITTED] ?: []
        def unversioned = result[UNVERSIONED] ?: []

        return new Scm.Status() {
            @Override
            String currentBranch() {
                return currentBranch
            }

            @Override
            List<String> uncommitted() {
                return uncommitted
            }

            @Override
            List<String> unversioned() {
                return unversioned
            }

            @Override
            int ahead() {
                return ahead
            }

            @Override
            int behind() {
                return behind
            }
        }
    }

    @Override
    void add(Object... source) {
        def files = project.files(source).collect { fixPath(it) }

        if (files) {
            // add all files to index
            exec('add', '--', files.join(' '))
        }
    }

    @Override
    void commit(String message) {
        // commit updated files
        exec('commit', '-m', message)
    }

    @Override
    void reset(String commit) {
        // reset changes
        exec('reset', commit)
    }

    @Override
    void tag(String tagName, String message) {
        // add tag
        exec('tag', '-a', tagName, '-m', message)
    }

    @Override
    void deleteTag(String tagName) {
        // delete tag
        exec('tag', '-d', tagName)
    }

    String exec(String... commandArgs) {
        def command = ['git', "--git-dir=${ fixPath(gitDir) }", "--work-tree=${ fixPath(workTree) }"]
        def out = new ByteArrayOutputStream()
        def err = new ByteArrayOutputStream()

        logger.info("Running ${ command.join(' ') } ${ commandArgs.join(' ') }")
        try {
            project.exec {
                commandLine command
                args commandArgs
                standardOutput = out
                errorOutput = err
            }
        } catch (GradleException e) {
            logger.debug("Output:\n${out}")
            fail("Cannot run command: ${ command.join(' ') } ${ commandArgs.join(' ') }\n${err}", e)
        }

        logger.debug("Output:\n${out}")
        if (err.toString()) {
            logger.warn("Error:\n${err}")
        }

        out.toString()
    }

    void fail(String message, Throwable throwable) {
        throw new GradleException(message, throwable)
    }

    String fixPath(File file) {
        return file.canonicalPath.replaceAll('\\\\', '/')
    }
}
