package com.zhuoxin.zhang.yitao.medol.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class GoodsDetailsAdapter extends PagerAdapter {
    private List<ImageView> list;

    public GoodsDetailsAdapter() {
        this.list = new ArrayList<>();
    }
    public void add(List data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView = list.get(position);
        ViewGroup parent = (ViewGroup) mImageView.getParent();

        if (parent != null) {
            parent.removeView(mImageView);
        }

        container.addView(mImageView);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);

        container.removeView(list.get(position));

    }
}
