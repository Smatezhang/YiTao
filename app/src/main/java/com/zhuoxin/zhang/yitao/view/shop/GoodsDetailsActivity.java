package com.zhuoxin.zhang.yitao.view.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.adapter.GoodsDetailsAdapter;
import com.zhuoxin.zhang.yitao.medol.entity.AvaterLoadOptions;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsDetail;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopApi;
import com.zhuoxin.zhang.yitao.medol.utils.ActivityUtils;
import com.zhuoxin.zhang.yitao.presenter.GoodsDetailsPresenter;
import com.zhuoxin.zhang.yitao.view.component.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;


public class GoodsDetailsActivity extends MvpActivity<GoodsDetailsView, GoodsDetailsPresenter> implements GoodsDetailsView {

    protected ProgressDialogFragment progressDialogFragment;
    @BindView(R.id.tv_goods_delete)
    TextView tvGoodsDelete;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.tv_detail_name)
    TextView tvDetailName;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_detail_master)
    TextView tvDetailMaster;
    @BindView(R.id.tv_detail_describe)
    TextView tvDetailDescribe;
    @BindView(R.id.btn_detail_message)
    Button btnDetailMessage;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.tv_goods_error)
    TextView tvGoodsError;
    @BindView(R.id.activity_goods_detail)
    RelativeLayout activityGoodsDetail;
    protected ActivityUtils activity;
    protected GoodsDetailsAdapter mGoodsDetailsAdapter;
    protected ArrayList<ImageView> mData;
    private static String UUID = "uuid";
    private static String TYPE = "type";
    protected String mUuid;
    protected int mType;

    public static Intent getIntent(Context context,String uuid,int type){
        Intent mIntent = new Intent(context, GoodsDetailsActivity.class);
        mIntent.putExtra(UUID,uuid);
        mIntent.putExtra(TYPE,type);
        return mIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        activity = new ActivityUtils(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUuid = getIntent().getStringExtra(UUID);
        mType = getIntent().getIntExtra(TYPE,0);
        if (mType == 1 ){
            btnDetailMessage.setVisibility(View.GONE);
            tvGoodsDelete.setVisibility(View.VISIBLE);
        }
        presenter.getGoodsDetails(mUuid);
        mGoodsDetailsAdapter = new GoodsDetailsAdapter();
        viewpager.setAdapter(mGoodsDetailsAdapter);

    }

    @NonNull
    @Override
    public GoodsDetailsPresenter createPresenter() {
        return new GoodsDetailsPresenter();
    }

    @Override
    public void showProgress() {
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) {
            return;
        }
        progressDialogFragment.show(getSupportFragmentManager(), getString(R.string.goodsDetails));
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void setImageData(List<String> arrayList) {
        mData = new ArrayList<>();

        for (String s:arrayList
             ) {
            ImageView mImageView = new ImageView(this);
            if (s != null){

                ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+s,mImageView, AvaterLoadOptions.build_item());
                mData.add(mImageView);
            }
        }
        mGoodsDetailsAdapter.add(mData);
        //原点指示器
        indicator.setViewPager(viewpager);
    }


    @Override
    public void setData(GoodsDetail data, User goods_user) {
        tvDetailName.setText(data.getName());//商品名称
        tvDetailPrice.setText(getString(R.string.goodsprice) + getString(R.string.goods_money, data.getPrice()));//2222￥
        tvDetailMaster.setText("发布者:" + goods_user.getNick_name());//发布者的昵称
        tvDetailDescribe.setText("商品描述:\n" + data.getDescription());//商品的描述
    }

    @Override
    public void showError() {
        tvGoodsError.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.goods_overdue);

    }

    @Override
    public void showMessage(String msg) {
        activity.showToast(msg);



    }

    @Override
    public void deleteEnd() {
        finish();
    }

    @OnClick({R.id.tv_goods_delete, R.id.btn_detail_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                break;
            case R.id.btn_detail_message:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
