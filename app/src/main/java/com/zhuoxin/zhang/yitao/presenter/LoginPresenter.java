package com.zhuoxin.zhang.yitao.presenter;

import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.zhuoxin.zhang.yitao.medol.LoginModel;
import com.zhuoxin.zhang.yitao.medol.listener.OnLoginListener;
import com.zhuoxin.zhang.yitao.medol.entity.UserResult;
import com.zhuoxin.zhang.yitao.view.me.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/8/15.
 */

public class LoginPresenter implements ILoginPresenter {
    private LoginModel loginModel;
    private LoginActivity loginActivity;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        loginModel = new LoginModel();
    }


    @Override
    public void login() {
        loginModel.login(loginActivity.getusername(), loginActivity.getpassword(), new OnLoginListener() {
            @Override
            public void successed(UserResult result) {

                loginActivity.hidepb();
                loginActivity.loginSuccess();
                loginActivity.showMsg("登录成功");

            }

            @Override
            public void failed(String s) {
                loginActivity.loginFailed();
                loginActivity.hidepb();
            }
        });
    }

    @Override
    public void attachView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //if (event.type!= HxEventType.LOGIN)return;
        //表示登录成功
        loginActivity.showMsg("登录成功");
        loginActivity.loginSuccess();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
       // if (event.type!=HxEventType.LOGIN)return;
        loginActivity.showMsg("环信地址不对");
    }
}
