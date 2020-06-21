package com.fishman.zxy.maspectj.permission.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.fishman.zxy.maspectj.permission.utils.PermissionUtil;

public class TransparentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDada();
    }



    private void initView() {
    }
    private void initDada() {
        Intent intent=getIntent();
        if(intent!=null){
            String [] value=intent.getStringArrayExtra(PermissionUtil.REQUEST_PERMISSIONS);
            int requstCode=intent.getIntExtra(PermissionUtil.REQUEST_CODE,PermissionUtil.REQUEST_CODE_DEFAULT);
            if(value==null||value.length<=0||requstCode==-1||PermissionUtil.permissionRequstCallback==null){
                finish();
                return;
            }
            //判断是否授权
            if(PermissionUtil.hasPermissionRequest(this,value)){
                PermissionUtil.permissionRequstCallback.permissionSuccess();
                finish();
                return;
            }
            //去申请权限
            ActivityCompat.requestPermissions(this,value,requstCode);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限申请成功
         if(PermissionUtil.requestPermissionSuccess(grantResults)){
             PermissionUtil.permissionRequstCallback.permissionSuccess();
             finish();
             return;
         }
         //权限拒绝，不在提示
         if(PermissionUtil.shouldShowRequestPermissionRationale(this,permissions)){
             PermissionUtil.permissionRequstCallback.permissionDenied();
             finish();
             return;
         }
         //权限拒绝
         PermissionUtil.permissionRequstCallback.permissionCancle();
         finish();


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
