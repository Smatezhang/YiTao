package com.zhuoxin.zhang.yitao.presenter;

import com.zhuoxin.zhang.yitao.medol.RegisterModel;
import com.zhuoxin.zhang.yitao.medol.listener.OnRegisterListener;
import com.zhuoxin.zhang.yitao.medol.user.UserResult;
import com.zhuoxin.zhang.yitao.view.me.RegisterActivity;

/**
 * Created by Administrator on 2017/8/16.
 */

public class RegisterPresenter implements IRegisterPresenter{
    private RegisterModel registerModel;
    private RegisterActivity registerActivity;

    public RegisterPresenter(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        registerModel = new RegisterModel(registerActivity);
    }

    @Override
    public void register() {
        registerModel.register(registerActivity.getUserName(), registerActivity.getPassword(),registerActivity.getRePassword(), new OnRegisterListener() {
            @Override
            public void successed(String s) {
                    registerActivity.registerSuccessed(s);
                    registerActivity.showpb();
            }

            @Override
            public void failed(String s) {
                registerActivity.registerFailed(s);
                registerActivity.hidepb();
            }
        });
    }
}
