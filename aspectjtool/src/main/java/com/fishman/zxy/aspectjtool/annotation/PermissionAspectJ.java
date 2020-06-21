package com.fishman.zxy.aspectjtool.annotation;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class PermissionAspectJ {
    /**
     * 声明切入点
     * @param permission  注解中的参数
     */
    @Pointcut("execution(@Permission * *(..))&& @annotation(permission)")
    public void getPermissin(Permission permission){}
    @Around("getPermissin(permission)")
    public void getPointMethod(ProceedingJoinPoint point, Permission permission) throws Throwable {
        point.proceed();
        Log.d("test--permission--"," "+permission.requestCode());
    }
}
