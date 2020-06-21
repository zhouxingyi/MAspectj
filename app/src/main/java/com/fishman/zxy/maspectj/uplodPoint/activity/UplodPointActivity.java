package com.fishman.zxy.maspectj.uplodPoint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fishman.zxy.maspectj.R;
import com.fishman.zxy.maspectj.uplodPoint.annotation.ParamesAnnotation;
import com.fishman.zxy.maspectj.uplodPoint.annotation.UploadPointData;

public class UplodPointActivity extends AppCompatActivity {
    Button  bt_Ui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_uplod_pint);
        initView();
        initData();
    }

    private void initView() {
        bt_Ui=findViewById (R.id.bt_ui);
    }

    private void initData() {

    }



    @UploadPointData
    private void setUserToUi(@ParamesAnnotation (key="name") String userId,@ParamesAnnotation (key="pwd") String password) {
        bt_Ui.setText ("name=  "+userId+"    pwd="+password);
    }

    public void setUi(View view) {
        setUserToUi("userId","password");
    }
}
