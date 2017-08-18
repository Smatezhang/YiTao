package com.zhuoxin.zhang.yitao.view.shop;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsDetail;
import com.zhuoxin.zhang.yitao.medol.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public interface GoodsDetailsView extends MvpView {

    void showProgress();//显示进度条
    void hideProgress();//隐藏进度条
    //设置图片路径
    void setImageData(List<String> arrayList);
    //设置商品信息
    void setData(GoodsDetail data, User goods_user);
    //商品不存在
    void showError();
    //提示信息
    void showMessage(String msg);
    //删除商品
    void deleteEnd();
}
