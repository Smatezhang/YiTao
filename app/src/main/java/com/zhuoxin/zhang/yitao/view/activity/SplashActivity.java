package com.zhuoxin.zhang.yitao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.utils.ConvertUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);//注册EventBus
        //账号冲突踢出
        if (getIntent().getBooleanExtra("AUTO_LOGIN", false)) {
            //清除本地缓存的用户信息
            CachePreferences.clearAllData();
            //踢出时，退出环信
            HxUserManager.getInstance().asyncLogout();
        }
        //环信自动登录的
        if (CachePreferences.getUser().getName() != null && !HxUserManager.getInstance().isLogin()) {
            User user = CachePreferences.getUser();
            EaseUser easeUser = ConvertUser.convert(user);
            HxUserManager.getInstance().asyncLogin(easeUser, user.getPassword());
            Log.d("TAG", "login");
            return;
        }


        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                jump();

            }
        }, 3000);
    }

    public void jump() {
        startActivity(new Intent(this, MainActivity.class));
        if(mTimer != null){
            mTimer.cancel();
        }

        finish();
    }


    @OnClick(R.id.tv_splash)
    public void onViewClicked() {
        jump();
    }

    @Override
    protected void onDestroy() {

        mTimer = null;
        EventBus.getDefault().unregister(this);//撤销注册
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event) {
        //判断是否登录成功
        //if (event.type == HxEventType.LOGIN) //登录不成功
        jump();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event) {
        //判断是否是登录失败的事件
        //if (event.type!=HxEventType.LOGIN)return;
        jump();
        throw new RuntimeException("login error");
    }
}
