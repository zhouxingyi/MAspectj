package com.fishman.zxy.maspectj.permission.aspectj;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.fishman.zxy.maspectj.permission.annotation.Permission;
import com.fishman.zxy.maspectj.permission.annotation.PermissionCancle;
import com.fishman.zxy.maspectj.permission.annotation.PermissionDenied;
import com.fishman.zxy.maspectj.permission.interfaces.PermissionRequstCallback;
import com.fishman.zxy.maspectj.permission.utils.PermissionUtil;

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

    /**
     * 获取切入的方法
     * @param point
     * @param permission
     * @throws Throwable
     */
    @Around("getPermissin(permission)")
    public void getPointMethod(final ProceedingJoinPoint point, Permission permission) throws Throwable {
        Context context = null;
        //获取上下文对象
        final Object thisContext=point.getThis();


        if(thisContext instanceof Context){
            context= (Context) thisContext;
        }else if(thisContext instanceof Fragment){
            context=((Fragment) thisContext).getActivity();
        }
        //判断权限和上下文 是否为null
        if(context==null ||permission==null ||permission.value().length<=0){
            return;
        }
        //获取权限数据
        String [] permissinValue=permission.value();
        final int requstCode=permission.requestCode();
        PermissionUtil.launchActivity(context, permissinValue, requstCode, new PermissionRequstCallback() {
            @Override
            public void permissionSuccess() {
                //权限申请成功 执行切入的方法
                try {
                    point.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void permissionCancle() {
               PermissionUtil.invokeAnnotation(thisContext, PermissionCancle.class,requstCode);
            }

            @Override
            public void permissionDenied() {
                PermissionUtil.invokeAnnotation(thisContext, PermissionDenied.class,requstCode);
            }
        });
        Log.d("test--permission--"," "+  point.getThis().getClass().getCanonicalName());
        Log.d("test--permission--"," "+  point.getThis().getClass().getName());
    }
}
