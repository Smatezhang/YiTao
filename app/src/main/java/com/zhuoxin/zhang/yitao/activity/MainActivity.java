package com.zhuoxin.zhang.yitao.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.base.BaseActivity;
import com.zhuoxin.zhang.yitao.fragement.MeFragment;
import com.zhuoxin.zhang.yitao.fragement.ShopFragment;
import com.zhuoxin.zhang.yitao.fragement.UnLoginFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tv_title)
    TextView mMainTvTitle;
    @BindView(R.id.main_tl)
    Toolbar mMainTl;
    @BindView(R.id.main_vp)
    ViewPager mMainVp;
    @BindViews({R.id.main_tv_market, R.id.main_tv_message, R.id.main_tv_people, R.id.main_tv_me})
   TextView[] tvs;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.text_color_hint)
    int textColorHint;
    private long exitsTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化
        init();
        //默认第一个为选中状态
        tvs[0].setTextColor(colorPrimary);//文字颜色
        tvs[0].setSelected(true);//图片配选中

    }

    private void init() {
        mMainVp.setAdapter(mFragmentStatePagerAdapter);
        mMainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (TextView tv:tvs
                        ) {
                    tv.setTextColor(textColorHint);
                    tv.setSelected(false);
                }
                //设置图片和字体颜色
                tvs[position].setSelected(true);
                ((TextView)tvs[position]).setTextColor(colorPrimary);
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
        for (int i = 0; i <tvs.length ; i++) {
            tvs[i].setTextColor(textColorHint);
            tvs[i].setSelected(false);
            tvs[i].setTag(i);//设置标记
        }


        //设置图片和字体颜色
        view.setSelected(true);
        ((TextView)view).setTextColor(colorPrimary);
        mMainVp.setCurrentItem(((int) view.getTag()));
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-exitsTime >2000){
            exitsTime = System.currentTimeMillis();
            showToast("再点击一次退出！！");
        }else finish();
    }


    private FragmentStatePagerAdapter mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
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
}
