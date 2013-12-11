package com.mutabra.gradle.plugins.release

import org.gradle.api.GradleException

/**
 * @author Ivan Khalopik
 */
class FinishReleaseSpec extends GitSpecification {

    def "should fail if defined tag already exists"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0'
        rootProject.release {
            update {
                tagName 'v0.0.3'
                nextVersion '0.0.4-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << '\n\n'
        }
        //tag already exist
        localGit.tag('v0.0.3', 'Existent tag')

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        thrown(GradleException)

        cleanup:
        localGit.removeTag('v0.0.3')
    }

    def "should not fail if defined tag doesn't exist"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0'
        rootProject.release {
            update {
                tagName 'v0.0.4'
                nextVersion '0.0.5-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << '\n\n'
        }
        //tag doesn't exist'

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        noExceptionThrown()

        cleanup:
        localGit.removeTag('v0.0.4')
    }

    def "should fail if next version matches current"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.5'
                nextVersion '1.0-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // there are no changes

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        thrown(GradleException)
    }

    def "should update project version to the next snapshot"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.5'
                nextVersion '0.0.6-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << '\n\n'
        }

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        rootProject.version == '0.0.6-SNAPSHOT'

        cleanup:
        localGit.removeTag('v0.0.5')
    }

    def "should update project version to the next snapshot in included sources"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.6'
                nextVersion '0.0.7-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << "\n\n"
        }

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        rootProject.file('build.gradle').text.contains("version = '0.0.7-SNAPSHOT'")

        cleanup:
        localGit.removeTag('v0.0.6')
    }

    def "should not update project version to the next snapshot in excluded sources"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.7'
                nextVersion '0.0.8-SNAPSHOT'
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << "\n\n"
        }

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        !rootProject.file('gradle.properties').text.contains("version = '0.0.8-SNAPSHOT'")

        cleanup:
        localGit.removeTag('v0.0.7')
    }

    def "should update project version to snapshot defined by closure against project"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.0-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.8'
                nextVersion { project.version - '-SNAPSHOT' + '-TEST' }
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << "\n\n"
        }

        when: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        rootProject.version == '1.0-TEST'
        rootProject.file('build.gradle').text.contains("version = '1.0-TEST'")

        cleanup:
        localGit.removeTag('v0.0.8')
    }

    def "should determine project snapshot version by closure at the moment of execution"() {
        given: 'pre-configured project'
        rootProject.apply plugin: ReleasePlugin
        rootProject.version = '1.1-SNAPSHOT'
        rootProject.release {
            update {
                tagName 'v0.0.9'
                nextVersion { project.version - '-SNAPSHOT' + '-TEST' }
                automaticVersions true
                source 'build.gradle'
            }
        }
        // fake changes
        rootProject.file('build.gradle').withWriterAppend {
            it << "\n\n"
        }

        when: 'project version changes'
        rootProject.version = '1.0-SNAPSHOT'
        and: 'finishRelease task executed'
        rootProject.finishRelease.execute()
        then:
        rootProject.version == '1.0-TEST'

        cleanup:
        localGit.removeTag('v0.0.9')
    }
}
