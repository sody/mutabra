/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */



package com.mutabra.gradle.plugins

import org.gradle.api.Project

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ReleasePluginConvention {

    Project project

    ReleaseSpec release

    ReleasePluginConvention(Project project) {
        this.project = project

        release = new ReleaseSpec(project)
    }

    void release(Closure closure) {
        release.configure(closure)
    }
}
