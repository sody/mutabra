/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ScmPlugin implements Plugin<Project> {
    public static final String SCM_EXTENSION = 'scm'

    @Override
    void apply(Project target) {
        def scm = findScm(target)
        if (scm != null) {
            target.extensions.add(SCM_EXTENSION, scm)
        }
    }

    static Scm findScm(Project target) {
        return target.rootProject.file('.git').exists() ?
            new GitScm(target) :
            null
    }
}
