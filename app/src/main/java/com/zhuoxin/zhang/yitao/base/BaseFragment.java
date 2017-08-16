package com.zhuoxin.zhang.yitao.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/8/15.
 */

public class BaseFragment extends Fragment {


    public void startActivity(Class mclass) {
        Intent intent = new Intent(getActivity(), mclass);
        startActivity(intent);
    }



}
