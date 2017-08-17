package com.zhuoxin.zhang.yitao.view.me;

import com.zhuoxin.zhang.yitao.medol.entity.UserResult;

/**
 * Created by Administrator on 2017/8/15.
 */

public interface ILoginActivity {
    String getpassword();

    String getusername();

    void loginSuccessed(UserResult result);

    void loginFailed(String s);

    void showpb();

    void hidepb();
}
