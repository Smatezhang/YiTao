package com.zhuoxin.zhang.yitao.medol.listener;

import com.zhuoxin.zhang.yitao.medol.user.UserResult;

/**
 * Created by Administrator on 2017/8/15.
 */

public interface OnLoginListener {
    void successed(UserResult result);
    void failed(String s);
}
