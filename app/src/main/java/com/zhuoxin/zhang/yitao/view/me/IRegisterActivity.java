package com.zhuoxin.zhang.yitao.view.me;

/**
 * Created by Administrator on 2017/8/16.
 */

public interface IRegisterActivity {
    String getUserName();

    String getPassword();
    String getRePassword();
    void showMsg(String msg);
    void registerSuccessed();

    void registerFailed();

    void showpb();

    void hidepb();

}