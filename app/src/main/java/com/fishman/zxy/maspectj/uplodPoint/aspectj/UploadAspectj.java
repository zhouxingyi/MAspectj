package com.fishman.zxy.maspectj.uplodPoint.aspectj;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.fishman.zxy.maspectj.uplodPoint.annotation.AnnotationBase;
import com.fishman.zxy.maspectj.uplodPoint.annotation.ParamesAnnotation;
import com.fishman.zxy.maspectj.uplodPoint.annotation.UploadPointData;
import com.fishman.zxy.maspectj.uplodPoint.body.PointBody;
import com.fishman.zxy.maspectj.uplodPoint.utils.HttpCallback;
import com.fishman.zxy.maspectj.uplodPoint.utils.HttpUtils;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 这个类将会被aspectj编译
 */
@Aspect
public class UploadAspectj {
    //切点，切入程序中所有被这个注解标记的方法
    @Pointcut("execution (@com.fishman.zxy.maspectj.uplodPoint.annotation.UploadPointData * *(..))")
    public void uplodPoint() {
    }

    //把方法切下来
    @Around("uplodPoint()")
    public void executionUploadPoint(ProceedingJoinPoint point) {
        //获取到方法的反射对象
        MethodSignature signature = (MethodSignature) point.getSignature ();
        Method method = signature.getMethod ();
        UploadPointData annotation1 = method.getAnnotation (UploadPointData.class);
        String url =annotation1.url ();
        //获取到方法的所有注解
        Annotation[] annotations = method.getAnnotations ();
        //获取到方法的所有接收的参数注解
        Annotation[] parameAnnotations=getMethodParameAnnotations(method);
        if(annotations==null||annotations.length<=0){
            //执行原有的方法
            backProceed(point);
        }
        //创建一个AnnotationBase
        AnnotationBase annotationBase=null;
        //遍历这个方法的所有注解‘
        for (Annotation annotation:annotations) {
            //获取到类型
            Class<? extends Annotation> annotationType = annotation.annotationType ();
            //获取到这个方法上的注解上的注解
            annotationBase=annotationType.getAnnotation (AnnotationBase.class);
            if(annotationBase==null){
             break;
            }
        }
        if(annotationBase==null){
            //执行原有的方法
            backProceed(point);
        }
        //获取注解得类型和id
        String type = annotationBase.type ();
        String actionId = annotationBase.actionId ();
        //获取方法得参数值
        Object[] args = point.getArgs ();
        //获取key对应得参数值得json数据
        JSONObject jsonObject=getData(parameAnnotations,args);
        //把收集起来的数据上传到服务器
        String msg = "上传埋点: " + "type: " + type + "  actionId:  " + actionId + "  data: " + jsonObject.toString();
        Log.d("-------------->",msg);
        PointBody pointBody=new PointBody ();
        pointBody.setType (type);
        pointBody.setActionId (actionId);
        pointBody.setData (jsonObject.toString());
        sendData(url,pointBody);
        //执行原有的方法
        backProceed(point);

    }

    private void sendData(String url, PointBody pointBody) {
        Log.d("url---",url);
        Log.d("pointBody---",pointBody.toString ());
        HttpUtils.getInstance ().post (url, pointBody, new HttpCallback () {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onfailure(IOException e) {

            }
        });
    }

    private void backProceed(ProceedingJoinPoint point) {
        try {
            point.proceed ();
        } catch (Throwable throwable) {
            throwable.printStackTrace ();
        }
        return;
    }

    /**
     * 封装参数和key为json数据
     * @param parameAnnotations
     * @param args
     * @return
     */
    private JSONObject getData(Annotation[] parameAnnotations, Object[] args) {
        JSONObject json=new JSONObject ();
        if(parameAnnotations==null||parameAnnotations.length<=0){
            return null;
        }
        for(int i=0;i<parameAnnotations.length;i++){
            Annotation parameAnnotation=parameAnnotations[i];
            if(parameAnnotation instanceof ParamesAnnotation){
                String parameName=((ParamesAnnotation) parameAnnotation).key ();
                json.put (parameName,args[i].toString ());

            }
        }

        return json;
    }

    /**
     * 获取参数的注解数组
     * @param method
     * @return
     */
    private Annotation[] getMethodParameAnnotations(Method method) {
        // 获取到方法接收参数的注解 以及注解上的注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations ();
        if(parameterAnnotations==null||parameterAnnotations.length==0){

            return null;
        }
        Annotation[] annotations=new Annotation [parameterAnnotations.length];
        int i=0;
        for (Annotation [] parmeters:parameterAnnotations) {
            for (Annotation annotation: parmeters) {
                annotations[i++]=annotation;
            }
        }
        return annotations;
    }
}