package com.fishman.zxy.maspectj.uplodPoint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 搜集数据的参数key
 */
@Target ({ElementType.PARAMETER,ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ParamesAnnotation {
    String key();
}
