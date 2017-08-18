package com.zhuoxin.zhang.yitao.presenter;

import android.text.Html;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsDetail;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsDetailResult;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopApi;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopClient;
import com.zhuoxin.zhang.yitao.medol.network.UICallBack;
import com.zhuoxin.zhang.yitao.view.shop.GoodsDetailsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/18.
 */


public class GoodsDetailsPresenter extends MvpNullObjectBasePresenter<GoodsDetailsView> {
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    @Override
    public void attachView(GoodsDetailsView view) {
        super.attachView(view);
    }
    //获取商品详情
    public void  getGoodsDetails(String uuid){
        getView().showProgress();
        EasyShopClient.getInstance().getGoodsData(uuid).enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String response) {
                GoodsDetailResult goodsDetailResult = new Gson().fromJson(response, GoodsDetailResult.class);
                if (goodsDetailResult.getCode() == 1){
                    getView().hideProgress();
                    getView().showMessage(goodsDetailResult.getMessage());
                    getView().setData(goodsDetailResult.getDatas(),goodsDetailResult.getUser());
                    List<GoodsDetail.ImageUri> pages = goodsDetailResult.getDatas().getPages();

                    ArrayList<String> list = new ArrayList<>();
                    for (GoodsDetail.ImageUri imageUri:pages
                         ) {
                        String uri = imageUri.getUri();
                        list.add(uri);
                    }
                    getView().setImageData(list);
                }else {
                    getView().showError();
                }

            }
        });

    }
    //删除商品
    public void delGoods(String uuid){
        getView().showProgress();
        EasyShopClient.getInstance().deleteGoods(uuid).enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {

                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String response) {

                getView().hideProgress();
                GoodsDetailResult goodsDetailResult = new Gson().fromJson(response, GoodsDetailResult.class);
                if (goodsDetailResult.getCode() == 1){
                    getView().deleteEnd();
                    getView().showMessage("删除成功！！");
                }else {
                    getView().showMessage("删除失败！！");
                }
            }
        });
    }
}