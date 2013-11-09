/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import com.mutabra.gradle.tasks.LessCompile
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessPlugin implements Plugin<Project> {
    public static final String COMPILE_LESS_TASK_NAME = "compileLess";

    public void apply(Project project) {
        LessPluginConvention convention = new LessPluginConvention(project);
        project.getConvention().getPlugins().put("less", convention);

        // configure all existent less tasks
        configureLessDefaults(project, convention);

        // add compile less task
        addCompileLess(project);
    }

    private void addCompileLess(Project project) {
        final LessCompile lessCompile = project.getTasks().create(COMPILE_LESS_TASK_NAME, LessCompile.class);
        lessCompile.setDescription("Compile LESS files into CSS files.");
        lessCompile.setGroup(BasePlugin.BUILD_GROUP);
    }

    private void configureLessDefaults(final Project project, final LessPluginConvention pluginConvention) {
        project.getTasks().withType(LessCompile.class, new Action<LessCompile>() {
            public void execute(LessCompile task) {
                task.destination(pluginConvention.getDestinationDir());
                task.source(pluginConvention.getSourceDir().files as File[]);
            }
        });
    }
}
