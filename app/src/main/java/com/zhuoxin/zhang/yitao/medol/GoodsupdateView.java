package com.zhuoxin.zhang.yitao.medol;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2017/8/22.
 */

public interface GoodsupdateView extends MvpView {
    void showPrb();
    void hidePrb();
    void upLoadSuccess();//上传成功
    void showMsg(String msg);

}
