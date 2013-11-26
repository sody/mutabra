/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.scm

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.slf4j.Logger

/**
 * @author Ivan Khalopik
 */
class GitScm implements Scm {
    private static final String INFO = 'info'
    private static final String UNVERSIONED = 'unversioned'
    private static final String UNCOMMITTED = 'uncommitted'

    private final Project project
    private final Logger logger

    GitScm(Project project) {
        this.project = project

        logger = project.logger
    }

    File getWorkTree() {
        return project.rootDir
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
        def files = files(source)
        if (files) {
            // add files to index
            exec('add', '--', files.join(' '))
        }
    }

    @Override
    void reset(Object... source) {
        def files = files(source)
        if (files) {
            // remove files from index
            exec('reset', '--', files.join(' '))
        }
    }

    @Override
    void commit(String message) {
        // commit changes
        exec('commit', '-m', message)
    }

    @Override
    void rollback(int commits) {
        // rollback changes
        exec('reset', '--hard', "HEAD~${commits}")
    }

    @Override
    void tag(String tagName, String message) {
        // add tag
        exec('tag', '-a', tagName, '-m', message)
    }

    @Override
    void removeTag(String tagName) {
        // remove tag
        exec('tag', '-d', tagName)
    }

    String exec(String... commandArgs) {
        logger.info("Running git ${ commandArgs.join(' ') }")

        def out = new ByteArrayOutputStream()
        def err = new ByteArrayOutputStream()

        try {
            project.exec {
                workingDir project.rootDir
                commandLine 'git'
                args commandArgs
                standardOutput = out
                errorOutput = err
            }
        } catch (GradleException e) {
            logger.debug("Output:\n${out}")
            throw new GradleException("Cannot run command: git ${ commandArgs.join(' ') }\n${err}", e)
        }

        logger.debug("Output:\n${out}")
        if (err.toString()) {
            logger.warn("Error:\n${err}")
        }

        out.toString()
    }

    private List<String> files(Object... source) {
        return project.files(source).collect { it.canonicalPath.replaceAll('\\\\', '/') }
    }
}
