package com.fishman.zxy.maspectj.uplodPoint.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记，上传用户行为的统计的注解
 */
@Target (ElementType.METHOD)     //作用域是方法上
@Retention (RetentionPolicy.RUNTIME)
@AnnotationBase (type = "EVENT",actionId = "1001")
public @interface UploadPointData {
   String url() default "http://xxx.com/uplodPoint";
}
