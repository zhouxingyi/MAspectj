package com.fishman.zxy.maspectj.permission.aspectj;

import android.util.Log;
import android.view.View;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class OnClickAspectj {
    public static int i;
    @Pointcut("execution(* onClick(View))&& args(v)")
    public void clickView(View v){}
    @Around("clickView(v)")
    public void clickViewPoint(ProceedingJoinPoint point,View v){
        i++;
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        Log.d("---","我是来自"+point.getThis().getClass().getName()+"被点击了"+i+"次");
    }
}
