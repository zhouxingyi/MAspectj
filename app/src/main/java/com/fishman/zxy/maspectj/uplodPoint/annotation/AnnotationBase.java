package com.fishman.zxy.maspectj.uplodPoint.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记每个注解的作用类型
 */
@Target (ElementType.ANNOTATION_TYPE)   //作用域是注解上
@Retention (RetentionPolicy.RUNTIME)
public @interface AnnotationBase {
    // 类型
    String type();
    //类型对应的ID
    String actionId();
}
