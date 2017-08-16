package com.zhuoxin.zhang.yitao.medol;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.listener.OnRegisterListener;

import com.zhuoxin.zhang.yitao.medol.network.EasyShopClient;
import com.zhuoxin.zhang.yitao.medol.network.UICallBack;
import com.zhuoxin.zhang.yitao.medol.user.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.user.Register;
import com.zhuoxin.zhang.yitao.medol.user.UserResult;
import com.zhuoxin.zhang.yitao.medol.utils.RegexUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/16.
 */

public class RegisterModel implements IRegisterModel {
    private Context context;

    public RegisterModel(Context context) {
        this.context = context;
    }

    @Override
    public void register(String userName, String passWord, final String repassword, final OnRegisterListener onRegisterListener) {
        //账号为中文，字母或数字，长度为4~20，一个中文算2个长度
        if (RegexUtils.verifyUsername(userName) != RegexUtils.VERIFY_SUCCESS) {//用正则表达式验证用户名  表示没有匹配上
            onRegisterListener.failed(context.getResources().getString(R.string.username_rules));//吐司：“账号为中文，字母或数字，长度为4~20，一个中文算2个长度”
            return;
            //密码以数字或字母开头，长度在6~18之间，只能包含字符、数字和下划线
        } else if (RegexUtils.verifyPassword(passWord) != RegexUtils.VERIFY_SUCCESS) {//密码没有匹配上
            onRegisterListener.failed(context.getResources().getString(R.string.password_rules));
            return;
        } else if (!TextUtils.equals(passWord, repassword)) {//两次输入的密码不同
            onRegisterListener.failed(context.getResources().getString(R.string.username_equal_pwd));//吐司：两次输入的密码不同！
            return;
        }


        final Call register = EasyShopClient.getInstance().register(userName, passWord);
        register.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                onRegisterListener.failed(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String response) {
                Register register = new Gson().fromJson(response, Register.class);
                Log.e("tag",register.toString());
                if (register.getCode() == 1) {

                    onRegisterListener.successed(register.getMsg());
                } else if (register.getCode() == 2) {
                    onRegisterListener.failed(register.getMsg());
                } else {
                    onRegisterListener.failed(register.getMsg());
                }


            }
        });

    }
}
