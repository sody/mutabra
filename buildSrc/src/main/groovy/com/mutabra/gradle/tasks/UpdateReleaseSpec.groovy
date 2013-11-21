package com.mutabra.gradle.tasks

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileTreeElement
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet

class UpdateReleaseSpec {

    Project project

    List<Object> source = new ArrayList<Object>()
    PatternFilterable patternSet = new PatternSet()

    UpdateReleaseSpec(Project project) {
        this.project = project

        source(project.buildFile)
    }

    Set<Project> getProjects() {
        return project.allprojects
    }

    FileTree getSource() {
        return this.source ? project.files(this.source).asFileTree : project.files().asFileTree;
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
