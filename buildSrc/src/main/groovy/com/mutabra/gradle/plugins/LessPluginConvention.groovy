package com.mutabra.gradle.plugins

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.api.internal.project.ProjectInternal

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessPluginConvention {
    ProjectInternal project

    String destinationDirName
    String sourceDirName

    LessPluginConvention(Project project) {
        this.project = project
        destinationDirName = 'resources/generatedCss'
        sourceDirName = 'src/main/less'
    }

    /**
     * Returns the directory to generate CSS into.
     *
     * @return The directory. Never returns null.
     */
    File getDestinationDir() {
        project.fileResolver.withBaseDir(project.buildDir).resolve(destinationDirName)
    }

    /**
     * Returns the directory to get LESS sources from.
     *
     * @return The directory. Never returns null.
     */
    FileTree getSourceDir() {
        project.fileTree(sourceDirName) {
            include '*.less'
        }
    }
}
