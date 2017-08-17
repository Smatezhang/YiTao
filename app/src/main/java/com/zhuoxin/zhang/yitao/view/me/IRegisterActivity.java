package com.zhuoxin.zhang.yitao.view.me;

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