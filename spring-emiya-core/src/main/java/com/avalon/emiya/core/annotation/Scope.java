package com.avalon.emiya.core.annotation;

import java.lang.annotation.*;

/**
 *
 * @author huhao
 * @since 2019/3/11 14:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Documented
public @interface Scope {

    String value() default ScopeType.SINGLETON;
}
