/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import com.mutabra.gradle.tasks.LessCompile
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.War

import java.util.concurrent.Callable

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessPlugin implements Plugin<Project> {
    void apply(final Project project) {
        project.getTasks().withType(LessCompile.class, new Action<LessCompile>() {
            public void execute(final LessCompile task) {
                task.source(new Callable() {
                    public Object call() throws Exception {
                        // include only base less files
                        return project.file('src/main/less');
                    }
                });
                task.include('*.less');
                task.setDestinationDir(project.file("${project.buildDir}/less"));
            }
        });

        final LessCompile lessCompile = project.getTasks().create('compileLess', LessCompile.class);
        lessCompile.setDescription("Compile LESS files into CSS files.");
        lessCompile.setGroup(BasePlugin.BUILD_GROUP);
    }
}
