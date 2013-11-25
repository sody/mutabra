/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins.less

import com.mutabra.gradle.plugins.less.LessCompile
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.plugins.jetty.JettyPlugin
import org.gradle.api.plugins.jetty.JettyRun
import org.gradle.api.plugins.jetty.internal.JettyPluginWebAppContext
import org.gradle.api.tasks.bundling.War

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessPlugin implements Plugin<Project> {
    public static final String COMPILE_LESS_TASK_NAME = "compileLess";

    private static final JETTY_HACK =
        '''
import org.gradle.api.plugins.jetty.internal.JettyPluginWebAppContext;
import org.mortbay.resource.ResourceCollection;

class JettyPluginWebAppContextExt extends JettyPluginWebAppContext {
    private final String[] resources;

    public JettyPluginWebAppContextExt(final String[] resources) {
        this.resources = resources;
    }

    void configure() {
        super.configure();

        // setup additional resource folders
        setBaseResource(new ResourceCollection(resources));
    }
}

return new JettyPluginWebAppContextExt(resources);
'''

    public void apply(Project project) {
        final LessPluginConvention convention = new LessPluginConvention(project);
        project.getConvention().getPlugins().put("less", convention);

        // configure all existent less tasks
        configureLessDefaults(project, convention);
        // configure all existent jetty tasks
        configureJettyDefaults(project);
        // configure all existent war tasks
        configureWarDefaults(project);

        // add compileLess task
        addCompileLess(project);
    }

    private void configureLessDefaults(final Project project, final LessPluginConvention pluginConvention) {
        project.getTasks().withType(LessCompile.class, new Action<LessCompile>() {
            public void execute(final LessCompile task) {
                task.destinationDir(pluginConvention.getDestinationDir());
                task.destination(pluginConvention.getDestination());
                task.source(pluginConvention.getSourceDir().files as File[]);
            }
        });
    }

    private void configureJettyDefaults(final Project project) {
        project.getTasks().withType(JettyRun.class, new Action<JettyRun>() {
            public void execute(final JettyRun task) {
                // apply only for automatic jettyRun task
                if (task.getName().equals(JettyPlugin.JETTY_RUN)) {
                    final LessCompile lessCompile = project.getTasks().getByName(COMPILE_LESS_TASK_NAME) as LessCompile;

                    // jettyRun should now depend on compileLess
                    task.dependsOn(lessCompile);

                    // multiple webapp folders hack
                    task.doFirst {
                        final JettyPluginWebAppContext context = new GroovyShell(JettyPluginWebAppContext.class.classLoader, new Binding([
                                'resources': [
                                        "${task.webAppSourceDirectory.canonicalPath}",
                                        "${lessCompile.destinationDir.canonicalPath}"
                                ] as String[]
                        ])).evaluate(JETTY_HACK) as JettyPluginWebAppContext;

                        task.setWebAppConfig(context);
                    }
                }
            }
        });
    }

    private void configureWarDefaults(final Project project) {
        project.getTasks().withType(War.class, new Action<War>() {
            public void execute(final War task) {
                // apply only for automatic jettyRun task
                if (task.getName().equals(WarPlugin.WAR_TASK_NAME)) {
                    final LessCompile lessCompile = project.getTasks().getByName(COMPILE_LESS_TASK_NAME) as LessCompile;

                    // jettyRun should now depend on compileLess
                    task.dependsOn(lessCompile);

                    // multiple webapp folders
                    task.from(lessCompile.getDestinationDir());
                }
            }
        });
    }

    private void addCompileLess(final Project project) {
        final LessCompile lessCompile = project.getTasks().create(COMPILE_LESS_TASK_NAME, LessCompile.class);
        lessCompile.setDescription("Compile LESS files into CSS files.");
        lessCompile.setGroup(BasePlugin.BUILD_GROUP);
    }
}
