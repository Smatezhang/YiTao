package com.zhuoxin.zhang.yitao.medol;

import android.util.Log;

import com.feicuiedu.apphx.model.HxUserManager;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.listener.OnLoginListener;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopClient;
import com.zhuoxin.zhang.yitao.medol.network.UICallBack;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.UserResult;
import com.zhuoxin.zhang.yitao.medol.utils.ConvertUser;

import java.io.IOException;

import okhttp3.Call;

        /*
         {
            "code": 1,
            "msg": "succeed",
            "data": {
            "username": "xc01",
            "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
            "nickname": "666",
            "name": "yt0186129caad847a98a71189fda4f2300",
            "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
            "password": "123456"
             }
         }
             */

        /*
        "username": "xc01",
        "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
        "nickname": "666",
        "name": "yt0186129caad847a98a71189fda4f2300",
        "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
        "password": "123456"
        */

/**
 * Created by Administrator on 2017/8/15.
 */

public class LoginModel implements ILoginModel {

    @Override
    public void login(String username, String password, final OnLoginListener onLoginListener) {
        Call call = EasyShopClient.getInstance().login(username, password);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                onLoginListener.failed(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String response) {
                UserResult result = new Gson().fromJson(response, UserResult.class);
                Log.e("tag",result.toString());
                int code = result.getCode();
                if (code == 1){
                    //保存数据
                    User mUser = result.getUser();
                    CachePreferences.setUser(mUser);
                    EaseUser easeUser= ConvertUser.convert(mUser);
                    HxUserManager.getInstance().asyncLogin(easeUser,mUser.getPassword());
                    onLoginListener.successed(result);
                }else if (code == 2){
                    onLoginListener.failed(result.getMessage());
                }else {
                    onLoginListener.failed(result.getMessage());
                }
            }
        });

    }
}
