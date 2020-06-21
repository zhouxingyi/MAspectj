package com.fishman.zxy.maspectj.permission.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fishman.zxy.maspectj.R;
import com.fishman.zxy.maspectj.permission.annotation.Permission;
import com.fishman.zxy.maspectj.permission.annotation.PermissionCancle;
import com.fishman.zxy.maspectj.permission.annotation.PermissionDenied;
import com.fishman.zxy.maspectj.permission.utils.PermissionUtil;

public class PermissionActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();
        initData();
        test("1111");
    }

    public void test(String s){

    }
    private void initView() {
        button=findViewById(R.id.bt_one);
    }

    private void initData() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id----","打印了"+ v.getId());

            }
        });
    }

    /**
     * 权限申请
     * @param
     */
    @Permission(value = Manifest.permission.READ_EXTERNAL_STORAGE,requestCode = 1)
    public void getSdcard() {
        Log.d("test----","打印了");
    }


    /**
     * 用户拒绝权限申请的回调方法
     * @param
     */
    @PermissionCancle(requstCode = 1)
    private void requestPermissionFailed() {
        Toast.makeText(this, "用户拒绝了权限", Toast.LENGTH_SHORT).show();
    }


    /**
     * 权限申请失败的回调方法
     * @param
     */
    @PermissionDenied(requstCode = 1)
    private void requestPermissionDenied() {
        Toast.makeText(this, "权限申请失败,不再询问", Toast.LENGTH_SHORT).show();
        PermissionUtil.startAndroidSettings(this);
    }
}
