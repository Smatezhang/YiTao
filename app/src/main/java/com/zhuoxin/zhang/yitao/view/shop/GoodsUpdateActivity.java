package com.zhuoxin.zhang.yitao.view.shop;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.zhang.yitao.medol.GoodsupdateView;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsDetail;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.presenter.GoodsUploadPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GoodsUpdateActivity extends MvpActivity<GoodsupdateView,GoodsUploadPresenter> implements GoodsDetailsView {

    @NonNull
    @Override
    public GoodsUploadPresenter createPresenter() {
        return new GoodsUploadPresenter();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setImageData(List<String> arrayList) {

    }

    @Override
    public void setData(GoodsDetail data, User goods_user) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void deleteEnd() {

    }
}
