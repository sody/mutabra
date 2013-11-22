/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileTreeElement
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet
import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

class UpdateReleaseSpec implements Configurable<UpdateReleaseSpec> {

    Project project

    List<Object> source = new ArrayList<Object>()
    PatternFilterable patternSet = new PatternSet()

    Object tag
    Object version
    Object nextVersion

    boolean automaticVersions;

    UpdateReleaseSpec(Project project) {
        this.project = project

        // default values
        source(project.buildFile)
        version {
            project.version - '-SNAPSHOT'
        }
        tag {
            'v' + project.version  - '-SNAPSHOT'
        }
        nextVersion {
            "${project.version}".replaceFirst(/(\d+)$/) {
                ((it[0] as int) + 1) + '-SNAPSHOT'
            }
        }
    }

    @Override
    UpdateReleaseSpec configure(Closure cl) {
        return ConfigureUtil.configure(cl, this, false)
    }

    Set<Project> getProjects() {
        return project.allprojects
    }

    @Input
    Object getTag() {
        return expand(tag)
    }

    UpdateReleaseSpec setTag(Object tag) {
        this.tag = tag
        return this
    }

    UpdateReleaseSpec tag(Object tag) {
        this.tag = tag
        return this
    }

    @Input
    Object getVersion() {
        return expand(version)
    }

    UpdateReleaseSpec setVersion(Object version) {
        this.version = version
        return this
    }

    UpdateReleaseSpec version(Object version) {
        this.version = version
        return this
    }

    @Input
    Object getNextVersion() {
        return expand(nextVersion)
    }

    UpdateReleaseSpec setNextVersion(Object nextVersion) {
        this.nextVersion = nextVersion
        return this
    }

    UpdateReleaseSpec nextVersion(Object nextVersion) {
        this.nextVersion = nextVersion
        return this
    }

    @Input
    boolean isAutomaticVersions() {
        return automaticVersions
    }

    UpdateReleaseSpec setAutomaticVersions(boolean automaticVersions) {
        this.automaticVersions = automaticVersions
        return this
    }

    UpdateReleaseSpec automaticVersions(boolean automaticVersions) {
        this.automaticVersions = automaticVersions
        return this
    }

    @InputFiles
    FileTree getSource() {
        return this.source ? project.files(this.source).asFileTree : project.files().asFileTree
    }

    UpdateReleaseSpec setSource(Object source) {
        this.source.clear()
        this.source.add(source)
        return this
    }

    UpdateReleaseSpec source(Object... sources) {
        for (Object source : sources) {
            this.source.add(source)
        }
        return this
    }

    UpdateReleaseSpec include(String... includes) {
        patternSet.include(includes)
        return this
    }

    UpdateReleaseSpec include(Iterable<String> includes) {
        patternSet.include(includes)
        return this
    }

    UpdateReleaseSpec include(Spec<FileTreeElement> includeSpec) {
        patternSet.include(includeSpec)
        return this
    }

    UpdateReleaseSpec include(Closure includeSpec) {
        patternSet.include(includeSpec)
        return this
    }

    UpdateReleaseSpec exclude(String... excludes) {
        patternSet.exclude(excludes)
        return this
    }

    UpdateReleaseSpec exclude(Iterable<String> excludes) {
        patternSet.exclude(excludes)
        return this
    }

    UpdateReleaseSpec exclude(Spec<FileTreeElement> excludeSpec) {
        patternSet.exclude(excludeSpec)
        return this
    }

    UpdateReleaseSpec exclude(Closure excludeSpec) {
        patternSet.exclude(excludeSpec)
        return this
    }

    Set<String> getIncludes() {
        return patternSet.includes
    }

    UpdateReleaseSpec setIncludes(Iterable<String> includes) {
        patternSet.includes = includes
        return this
    }

    Set<String> getExcludes() {
        return patternSet.excludes
    }

    UpdateReleaseSpec setExcludes(Iterable<String> excludes) {
        patternSet.excludes = excludes
        return this
    }

    Object expand(Object value) {
        return value instanceof Closure ? value.call() : value
    }
}