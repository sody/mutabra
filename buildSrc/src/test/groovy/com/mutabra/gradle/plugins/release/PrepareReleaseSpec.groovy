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
    }

    def "prepareRelease should fail if there are snapshot dependencies"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnSnapshotDependencies true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are snapshot dependencies'
        rootProject.configurations.create('snapshots')
        rootProject.dependencies.add('snapshots', 'com.example:example:1.3-SNAPSHOT')
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        thrown(GradleException)
    }

    def "prepareRelease should not fail if there are no snapshot dependencies"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            verify {
                failOnSnapshotDependencies true
            }
            update {
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'there are no snapshot dependencies'
        rootProject.configurations.create('snapshots')
        rootProject.dependencies.add('snapshots', 'com.example:example:1.3')
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        noExceptionThrown()
    }

    def "prepareRelease should update project version to new release"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                releaseVersion '1.0.0'
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        rootProject.version == '1.0.0'
    }

    def "prepareRelease should update project version to new release in included sources"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                releaseVersion '1.0-beta-1'
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        rootProject.file('build.gradle').text.contains("version = '1.0-beta-1'")
    }

    def "prepareRelease should not update project version to new release in excluded sources"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                releaseVersion '1.0-beta-1'
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        !rootProject.file('gradle.properties').text.contains("version = '1.0-beta-1'")
    }

    def "prepareRelease should update project version to release defined by closure against project"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                releaseVersion { project.version - '-SNAPSHOT' + '-alpha-1' }
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        rootProject.version == '1.0-alpha-1'
        rootProject.file('build.gradle').text.contains("version = '1.0-alpha-1'")
    }

    def "prepareRelease should determine project release version by closure at the moment of execution"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                releaseVersion { project.version - '-SNAPSHOT' + '-alpha-1' }
                automaticVersions true
                source 'build.gradle'
            }
        }

        when: 'project version changes'
        rootProject.version = '1.1'
        and: 'prepareRelease task executed'
        rootProject.prepareRelease.execute()
        then:
        rootProject.version == '1.1-alpha-1'
    }
}
