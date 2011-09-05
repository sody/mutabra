package com.mutabra.web.annotations;

import org.apache.tapestry5.ioc.annotations.UseWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD, CONSTRUCTOR, METHOD})
@UseWith({COMPONENT, MIXIN, PAGE, SERVICE})
public @interface Custom {
}
