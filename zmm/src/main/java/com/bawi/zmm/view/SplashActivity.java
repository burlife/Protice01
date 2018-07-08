package com.bawi.zmm.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bawi.zmm.MainActivity;
import com.bawi.zmm.R;

/**
 * Created by 1 on 2018/7/7.
 */

public class SplashActivity extends AppCompatActivity{
    CountDownView downText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        downText=findViewById(R.id.downText);
        //开始的时候
        downText.start(this, MainActivity.class);
        downText.setListener(new CountDownView.MyListener() {
            @Override
            public void onClick(View view) {
                //跳转
                downText.stop();
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
