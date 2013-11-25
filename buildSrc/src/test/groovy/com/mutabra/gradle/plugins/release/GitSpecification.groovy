package com.mutabra.gradle.plugins.release

import com.mutabra.gradle.plugins.scm.GitScm
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Shared
import spock.lang.Specification

import static com.mutabra.gradle.plugins.scm.GitScm.fixPath

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
        new File(remoteGit.workTree, 'build.gradle').withWriter {
            it << "group = 'com.example'"
            it << "description = 'Test'"
        }
        remoteGit.add('build.gradle')
        remoteGit.commit('First commit')
        // second commit
        new File(remoteGit.workTree, 'gradle.properties').withWriter {
            it << "testProp1=Test1"
            it << "testProp2=Test2"
        }
        remoteGit.add('gradle.properties')
        remoteGit.commit('Second commit')

        def localProject = ProjectBuilder.builder()
                .withName("FakeRemoteProject")
                .withProjectDir(new File(testDir, 'local'))
                .build()

        // setup local git
        localGit = new GitScm(localProject)
        localGit.exec('init')
        localGit.exec('remote', 'add', 'origin', 'file://' + fixPath(remoteGit.gitDir))
        localGit.exec('remote', 'update')
        localGit.exec('merge', 'origin/master')
        localGit.exec('branch', '--set-upstream', 'master', 'origin/master')
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
}
