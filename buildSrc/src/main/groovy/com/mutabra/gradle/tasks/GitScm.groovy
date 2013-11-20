/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.api.GradleException

/**
 * @author Ivan Khalopik
 */
public class GitScm implements Scm {
    private static final String INFO = 'info'
    private static final String UNVERSIONED = 'unversioned'
    private static final String UNCOMMITTED = 'uncommitted'

    File workTree;

    GitScm(File workTree) {
        this.workTree = workTree
    }

    @Override
    Scm.Status status() {
//        exec('remote', 'update')

        final Map<String, List<String>> result = exec('status', '-sb').readLines().groupBy {
            it ==~ /^\s*#{2}.*/ ?
                INFO :
                it ==~ /^\s*\?{2}.*/ ?
                    UNVERSIONED :
                    UNCOMMITTED
        }

        String info = result[INFO][0]
        String currentBranch = (info =~ /^\s*\#{2}\s*([^\.{3}]*).*/)[0][1]
        int ahead = info ==~ /.*ahead (\d+).*/ ? Integer.parseInt((info =~ /.*ahead (\d+).*/)[0][1]) : 0
        int behind = info ==~ /.*behind (\d+).*/ ? Integer.parseInt((info =~ /.*behind (\d+).*/)[0][1]) : 0
        List<String> uncommitted = result[UNCOMMITTED] ?: []
        List<String> unversioned = result[UNVERSIONED] ?: []

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
                return ahead;
            }

            @Override
            int behind() {
                return behind
            }
        }
    }

    String exec(String... commands) {
        exec(workTree, commands)
    }

    String exec(File workDir, String... commands) {
        def cmdLine = ['git']
        cmdLine.addAll commands
        execute(true, ['HOME': System.getProperty("user.home")], workDir, cmdLine as String[])
    }

    String execute(boolean failOnStderr = true, Map env = [:], File directory = null, String... commands) {
        def out = new StringBuffer()
        def err = new StringBuffer()
        def logMessage = "Running \"${commands.join(' ')}\"${ directory ? ' in [' + directory.canonicalPath + ']' : '' }"
        def process = (env || directory) ?
            (commands as List).execute(env.collect { "$it.key=$it.value" } as String[], directory) :
            (commands as List).execute()

        process.waitForProcessOutput(out, err)

        if (err.toString()) {
            def message = "$logMessage produced an error: [${err.toString().trim()}]"
            if (failOnStderr) {
                throw new GradleException(message)
            } else {
//                log.warn(message)
            }
        }
        out.toString()
    }
}
