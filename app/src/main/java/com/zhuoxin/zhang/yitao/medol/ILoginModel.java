package com.zhuoxin.zhang.yitao.medol;

import com.zhuoxin.zhang.yitao.medol.listener.OnLoginListener;

/**
 * Created by Administrator on 2017/8/15.
 */

public interface ILoginModel {
    void login(String username, String password, OnLoginListener onLoginListener);
}
