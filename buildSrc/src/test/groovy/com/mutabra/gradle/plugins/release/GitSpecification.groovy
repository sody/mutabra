package com.mutabra.gradle.plugins.release

import com.mutabra.gradle.plugins.scm.GitScm
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
abstract class GitSpecification extends Specification {

    @Shared
    GitScm remoteGit

    @Shared
    GitScm localGit

    Project rootProject

    def setupSpec() {
        // setup test project dir
        def testDir = new File("build/tmp/test/${getClass().simpleName}")
        if (testDir.exists()) {
            testDir.deleteDir()
        }
        testDir.mkdirs()

        // setup remote git
        def remoteProject = ProjectBuilder.builder()
                .withName("FakeRemoteProject")
                .withProjectDir(new File(testDir, 'remote'))
                .build()
        remoteGit = new GitScm(remoteProject)
        remoteGit.exec('init')
        // first commit
        remoteProject.file('build.gradle').withWriter {
            it << "group = 'com.example'\n"
            it << "description = 'Test'\n"
            it << "version = '1.0-SNAPSHOT'\n"
        }
        remoteGit.add('build.gradle')
        remoteGit.commit('First commit')
        // second commit
        remoteProject.file('gradle.properties').withWriter {
            it << "testProp1=1.0-SNAPSHOT\n"
            it << "testProp2=1.0\n"
            it << "testProp3=1.1\n"
        }
        remoteGit.add('gradle.properties')
        remoteGit.commit('Second commit')

        // add default state pointer
        remoteGit.tag('original-version', 'Original version pointer added.')

        // clone to local repo
        remoteGit.exec('clone', '-l', '.', '../local')

        // setup local git
        def localProject = ProjectBuilder.builder()
                .withName("FakeLocalProject")
                .withProjectDir(new File(testDir, 'local'))
                .build()
        localGit = new GitScm(localProject)
    }

    def cleanupSpec() {
        localGit = null
        remoteGit = null
    }

    def setup() {
        rootProject = ProjectBuilder.builder()
                .withName("RootProject")
                .withProjectDir(localGit.workTree)
                .build()
    }

    def cleanup() {
        remoteGit.exec('reset', '--hard', 'original-version')
        localGit.exec('reset', '--hard', 'original-version')
    }
}
