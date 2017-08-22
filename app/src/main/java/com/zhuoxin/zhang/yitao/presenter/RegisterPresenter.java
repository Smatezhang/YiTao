package com.zhuoxin.zhang.yitao.presenter;

import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.zhuoxin.zhang.yitao.medol.RegisterModel;
import com.zhuoxin.zhang.yitao.medol.listener.OnRegisterListener;
import com.zhuoxin.zhang.yitao.view.me.RegisterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



public class RegisterPresenter implements IRegisterPresenter{
    private RegisterModel registerModel;
    private RegisterActivity registerActivity;

    public RegisterPresenter(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        registerModel = new RegisterModel(registerActivity);
    }

    @Override
    public void register() {
        registerActivity.showpb();

        registerModel.register(registerActivity.getUserName(), registerActivity.getPassword(),registerActivity.getRePassword(), new OnRegisterListener() {
            @Override
            public void successed(String s) {
                    registerActivity.registerSuccessed();

                    registerActivity.showMsg("登录成功");
                    registerActivity.hidepb();
            }

            @Override
            public void failed(String s) {
                registerActivity.registerFailed();
                registerActivity.hidepb();
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
        registerActivity.showMsg("登录成功");
        registerActivity.registerSuccessed();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        //if (event.type!=HxEventType.LOGIN)return;
        registerActivity.showMsg("环信地址不对");
    }

}
