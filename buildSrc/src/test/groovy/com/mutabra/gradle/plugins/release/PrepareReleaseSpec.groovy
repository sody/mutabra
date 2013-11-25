package com.mutabra.gradle.plugins.release

import org.gradle.api.GradleException

/**
 * @author Ivan Khalopik
 */
class PrepareReleaseSpec extends GitSpecification {

    def "prepareRelease should fail if branch doesn't match defined"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                requireBranch 'develop'
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)
    }

    def "prepareRelease should not fail if branch matches defined"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                requireBranch 'master'
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }


    def "prepareRelease should fail if there are uncommitted changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnCommitNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are uncommitted changes'
        rootProject.file('README').withWriter {
            it << 'Hello, World!'
        }
        localGit.add('README')
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)

        cleanup:
        rootProject.file('README').delete()
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should not fail if there are no uncommitted changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnCommitNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should fail if there are unversioned changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnUnversionedFiles true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are unversioned changes'
        rootProject.file('README').withWriter {
            it << 'Hello, World!'
        }
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)

        cleanup:
        rootProject.file('README').delete()
    }

    def "prepareRelease should not fail if there are no unversioned changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnUnversionedFiles true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should fail if there are outcoming changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnPublishNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are outcoming changes'
        rootProject.file('README').withWriter {
            it << 'Hello, World!'
        }
        localGit.add('README')
        localGit.commit('Added README file')
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should not fail if there are no outcoming changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnPublishNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should fail if there are incoming changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnUpdateNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are incoming changes'
        localGit.exec('reset', '--hard', 'HEAD^')
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }

    def "prepareRelease should not fail if there are no incoming changes"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnUpdateNeeded true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.exec('reset', '--hard', 'origin/master')
    }
}
