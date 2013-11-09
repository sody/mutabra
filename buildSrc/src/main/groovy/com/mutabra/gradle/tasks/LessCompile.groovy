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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LessCompile extends SourceTask {
    private static Logger logger = LoggerFactory.getLogger(LessCompile.class);

    private boolean compress;
    private Object destination;

    @OutputDirectory
    public File getDestination() {
        return getProject().file(destination);
    }

    public LessCompile destination(final Object destination) {
        this.destination = destination;
        return this;
    }

    @Input
    public boolean isCompress() {
        return compress;
    }

    public LessCompile compress(final boolean compress) {
        this.compress = compress;
        return this;
    }

    @TaskAction
    protected void compile() {
        // create less compiler instance
        final LessCompiler compiler = new LessCompiler(compress: compress);

        logger.info("Compiling LESS files to ${destination}...");
        final File destination = getDestination();
        getSource().visit { source ->
            if (!source.directory) {
                try {
                    final File sourceFile = source.file;
                    final File targetFile = getProject().file("${destination}/${source.path.replace('.less', '.css')}");

                    logger.debug("Compiling ${sourceFile} to ${targetFile}...");
                    compiler.compile(sourceFile, targetFile);
                } catch (LessException e) {
                    throw new GradleException("Cannot compile source file ${source.name}: ${e.message}", e);
                }
            }
        }
    }
}
