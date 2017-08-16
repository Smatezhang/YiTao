package com.zhuoxin.zhang.yitao.view.me;

import com.zhuoxin.zhang.yitao.medol.user.UserResult;

/**
 * Created by Administrator on 2017/8/16.
 */

public interface IRegisterActivity {
    String getUserName();

    String getPassword();
    String getRePassword();

    void registerSuccessed(String s);

    void registerFailed(String s);

    void showpb();

    void hidepb();

}