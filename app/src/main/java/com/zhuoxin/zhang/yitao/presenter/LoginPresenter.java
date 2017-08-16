package com.zhuoxin.zhang.yitao.presenter;

import com.zhuoxin.zhang.yitao.medol.LoginModel;
import com.zhuoxin.zhang.yitao.medol.listener.OnLoginListener;
import com.zhuoxin.zhang.yitao.medol.user.UserResult;
import com.zhuoxin.zhang.yitao.view.me.LoginActivity;

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
                loginActivity.loginSuccessed(result);
                loginActivity.showpb();


            }

            @Override
            public void failed(String s) {
                loginActivity.loginFailed(s);
                loginActivity.hidepb();
            }
        });
    }
}
