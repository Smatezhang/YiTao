package com.zhuoxin.zhang.yitao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zhuoxin.zhang.yitao.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/8/11.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.tv_splash)
    TextView mTvSplash;
    protected Timer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //getSupportActionBar().hide();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                jump();
                finish();
            }
        },3000);
    }
    public void jump(){
        startActivity(new Intent(this,MainActivity.class));

    }


    @OnClick(R.id.tv_splash)
    public void onViewClicked() {
        jump();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer =null;
    }
}
