package com.zhuoxin.zhang.yitao.view.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoxin.zhang.yitao.R;


public class ShopFragment extends Fragment {

    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView==null){

            mView = inflater.inflate(R.layout.fragment_shop, container, false);
        }
        return mView;
    }

}
