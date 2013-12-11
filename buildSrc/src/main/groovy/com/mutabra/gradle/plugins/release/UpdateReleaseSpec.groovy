/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.release

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileTreeElement
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet
import org.gradle.util.Configurable
import org.gradle.util.ConfigureUtil

/**
 * @author Ivan Khalopik
 */
class UpdateReleaseSpec implements Configurable<UpdateReleaseSpec> {

    private final Project project

    private final List<Object> source = new ArrayList<Object>()
    private final PatternFilterable patternSet = new PatternSet()

    Object tagName
    Object releaseVersion
    Object nextVersion
    boolean automaticVersions

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

    @Override
    UpdateReleaseSpec configure(Closure cl) {
        return ConfigureUtil.configure(cl, this, false)
    }

    UpdateReleaseSpec tagName(Object tag) {
        this.tagName = tag
        return this
    }

    UpdateReleaseSpec releaseVersion(Object version) {
        this.releaseVersion = version
        return this
    }

    UpdateReleaseSpec nextVersion(Object nextVersion) {
        this.nextVersion = nextVersion
        return this
    }

    UpdateReleaseSpec automaticVersions(boolean automaticVersions) {
        this.automaticVersions = automaticVersions
        return this
    }

    FileTree getSource() {
        return source ? project.files(source).asFileTree : project.files().asFileTree
    }

    void setSource(Object source) {
        this.source.clear()
        this.source.add(source)
    }

    UpdateReleaseSpec source(Object... sources) {
        for (Object source : sources) {
            this.source.add(source)
        }
        return this
    }

    Set<String> getIncludes() {
        return patternSet.includes
    }

    void setIncludes(Iterable<String> includes) {
        patternSet.includes = includes
    }

    Set<String> getExcludes() {
        return patternSet.excludes
    }

    void setExcludes(Iterable<String> excludes) {
        patternSet.excludes = excludes
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
}
