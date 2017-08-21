package com.zhuoxin.zhang.yitao.presenter;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.zhang.yitao.medol.PersonInfoView;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.UserResult;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopClient;
import com.zhuoxin.zhang.yitao.medol.network.UICallBack;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/21.
 */

public class PersonPresenter extends MvpNullObjectBasePresenter<PersonInfoView> {
    protected Call mCall;

    @Override
    public void detachView(boolean retainInstance) {
       if (mCall !=null){
           mCall.cancel();
       }

        super.detachView(retainInstance);
    }

    /**
     * 上传
     * @param mFile
     */
    public void updateAvatar(File mFile){
        getView().showPrb();
        mCall = EasyShopClient.getInstance().uploadAvatar(mFile);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg("网络请求错误！");
            }

            @Override
            public void onResponseUI(Call call, String response) {
                getView().hidePrb();
                UserResult mUserResult = new Gson().fromJson(response, UserResult.class);
                if (mUserResult.getCode() == 1){
                    getView().showMsg(" 上传成功");
                    CachePreferences.setUser(mUserResult.getUser());
                }else {
                    getView().showMsg(mUserResult.getMessage());
                }
            }
        });
    }
}
