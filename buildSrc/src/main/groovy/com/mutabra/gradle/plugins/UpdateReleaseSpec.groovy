/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileTreeElement
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet
import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

class UpdateReleaseSpec implements Configurable<UpdateReleaseSpec> {

    Project project

    Object tagName
    Object releaseVersion
    Object nextVersion
    boolean automaticVersions;

    List<Object> source = new ArrayList<Object>()
    PatternFilterable patternSet = new PatternSet()

    UpdateReleaseSpec(Project project) {
        this.project = project

        // default values
        releaseVersion = { project.version - '-SNAPSHOT' }
        tagName = { "v${project.version}" }
        nextVersion = {
            "${project.version}".replaceFirst(/(\d+)$/) {
                ((it[0] as int) + 1) + '-SNAPSHOT'
            }
        }

        source(project.buildFile)
    }

    UpdateReleaseSpec configure(Closure cl) {
        return ConfigureUtil.configure(cl, this, false)
    }

    Set<Project> getProjects() {
        return project.allprojects
    }

    Object getTagName() {
        return tagName
    }

    UpdateReleaseSpec setTagName(Object tag) {
        this.tagName = tag
        return this
    }

    UpdateReleaseSpec tagName(Object tag) {
        this.tagName = tag
        return this
    }

    Object getReleaseVersion() {
        return releaseVersion
    }

    UpdateReleaseSpec setReleaseVersion(Object version) {
        this.releaseVersion = version
        return this
    }

    UpdateReleaseSpec releaseVersion(Object version) {
        this.releaseVersion = version
        return this
    }

    Object getNextVersion() {
        return nextVersion
    }

    UpdateReleaseSpec setNextVersion(Object nextVersion) {
        this.nextVersion = nextVersion
        return this
    }

    UpdateReleaseSpec nextVersion(Object nextVersion) {
        this.nextVersion = nextVersion
        return this
    }

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
}
