package com.zhuoxin.zhang.yitao.medol;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/8/21.
 */

public interface PersonInfoView extends MvpView{
    void showPrb();//显示progressbar
    void hidePrb();//隐藏progressbar
    void showMsg(String msg);//吐司
    //用来更新头像
    void updateAvatar(String url);


}
