package com.zhuoxin.zhang.yitao.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.feicuiedu.apphx.presentation.contact.list.HxContactListFragment;
import com.feicuiedu.apphx.presentation.conversation.HxConversationListFragment;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.view.base.BaseActivity;
import com.zhuoxin.zhang.yitao.view.fragement.UnLoginFragment;
import com.zhuoxin.zhang.yitao.view.me.MeFragment;
import com.zhuoxin.zhang.yitao.view.shop.ShopFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {


    @BindViews({R.id.main_tv_market, R.id.main_tv_message, R.id.main_tv_people, R.id.main_tv_me})
    TextView[] tvs;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.text_color_hint)
    int textColorHint;
    @BindView(R.id.main_tv_title)
    TextView mMainTvTitle;
    @BindView(R.id.main_tl)
    Toolbar mMainTl;
    @BindView(R.id.main_vp)
    ViewPager mMainVp;

    private long exitsTime;
    protected int mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //默认第一个为选中状态
        tvs[0].setTextColor(colorPrimary);//文字颜色
        tvs[0].setSelected(true);//图片配选中

    }

    @Override
    protected void onStart() {
        //初始化
        init();
        super.onStart();
    }

    private void init() { //判断用户登录
        if (CachePreferences.getUser().getName()==null){//没有登录过
            mMainVp.setAdapter(munloginFragmentStatePagerAdapter);//向viewpager中添加未登录的适配器

        }else{//登录过的
            mMainVp.setAdapter(mloginFragmentStatePagerAdapter);//向viewpager中添加登录过的适配器e

        }

        mMainVp.setCurrentItem(mTag);
        mMainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (TextView tv : tvs
                        ) {
                    tv.setTextColor(textColorHint);
                    tv.setSelected(false);
                }
                //设置图片和字体颜色
                tvs[position].setSelected(true);
                ((TextView) tvs[position]).setTextColor(colorPrimary);
                mMainTvTitle.setText(tvs[position].getText());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.main_tv_market, R.id.main_tv_message, R.id.main_tv_people, R.id.main_tv_me})
    public void onViewClicked(View view) {
        //初始化状态
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setTextColor(textColorHint);
            tvs[i].setSelected(false);
            tvs[i].setTag(i);//设置标记
        }


        //设置图片和字体颜色
        view.setSelected(true);
        ((TextView) view).setTextColor(colorPrimary);
        mTag = (int) view.getTag();
        mMainVp.setCurrentItem(mTag);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitsTime > 2000) {
            exitsTime = System.currentTimeMillis();
            showToast("再点击一次退出！！");
        } else finish();
    }


    private FragmentStatePagerAdapter munloginFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShopFragment();
                case 1:
                    return new UnLoginFragment();
                case 2:
                    return new UnLoginFragment();
                case 3:
                    return new MeFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return tvs.length;
        }


    };
 private FragmentStatePagerAdapter mloginFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0://当位置是0时显示"市场"的Fragment
                    return new ShopFragment();
                case 1://当位置是1显示"信息"的Fragment
                    return new HxConversationListFragment();
                case 2://当位置是2显示"通讯录"的Fragment
                    return new HxContactListFragment();
                case 3://当位置是3显示"我的"Fragment
                    return new MeFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return tvs.length;
        }


    };

    @Override
    protected void onDestroy() {
        CachePreferences.clearAllData();
        super.onDestroy();
    }
}
