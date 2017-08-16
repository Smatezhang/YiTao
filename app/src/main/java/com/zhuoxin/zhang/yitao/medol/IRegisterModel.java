package com.zhuoxin.zhang.yitao.medol;

import com.zhuoxin.zhang.yitao.medol.listener.OnRegisterListener;

/**
 * Created by Administrator on 2017/8/16.
 */

public interface IRegisterModel {
    void register(String userName, String passWord, String repassword,OnRegisterListener onRegisterListener);
}
