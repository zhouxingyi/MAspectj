package com.fishman.zxy.maspectj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.fishman.zxy.maspectj.permission.activity.PermissionActivity;
import com.fishman.zxy.maspectj.uplodPoint.activity.UplodPointActivity;


public class MainActivity extends AppCompatActivity {
    Button buPermission;
    Button btUplodPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }
    private void initView() {
        buPermission=findViewById(R.id.bt_one);
        btUplodPoint=findViewById (R.id.bt_two);
    }

    private void initData() {
        buPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (MainActivity.this, PermissionActivity.class);
                startActivity (intent);

            }
        });
        btUplodPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (MainActivity.this, UplodPointActivity.class);
                startActivity (intent);

            }
        });
    }


}
