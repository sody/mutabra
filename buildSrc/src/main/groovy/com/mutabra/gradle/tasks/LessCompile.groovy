/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

package com.mutabra.gradle.tasks

import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.lesscss.LessCompiler
import org.lesscss.LessException

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessCompile extends SourceTask {
    private boolean compress = false
    private File destinationDir;

    @OutputDirectory
    public File getDestinationDir() {
        return destinationDir;
    }

    public void setDestinationDir(final File destinationDir) {
        this.destinationDir = destinationDir;
    }

    @Input
    public boolean isCompress() {
        return compress;
    }

    public void setCompress(final boolean compress) {
        this.compress = compress;
    }

    @TaskAction
    protected void compile() {
        // create less compiler instance
        final LessCompiler compiler = new LessCompiler(compress: compress);

        final File destination = getDestinationDir();
        getSource().each { source ->
            try {
                compiler.compile(source, project.file("${destination}/${source.name}".replace('.less', '.css')), false);
            } catch (LessException e) {
                throw new GradleException("Cannot compile source file ${source.name}: ${e.message}", e);
            }
        }
    }
}
