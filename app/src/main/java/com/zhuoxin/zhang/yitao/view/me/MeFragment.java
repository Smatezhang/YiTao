package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.base.BaseFragment;
import com.zhuoxin.zhang.yitao.medol.user.CachePreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MeFragment extends BaseFragment {
    @BindView(R.id.me_civ)
    CircularImageView meCiv;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.iv_my_information)
    TextView ivMyInformation;
    @BindView(R.id.iv_foods)
    TextView ivFoods;
    @BindView(R.id.iv_upload)
    TextView ivUpload;
    Unbinder unbinder;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_message, container, false);
        }

        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_civ, R.id.tv_login_register, R.id.iv_my_information, R.id.iv_foods, R.id.iv_upload})
    public void onViewClicked(View view) {
            if(CachePreferences.getUser().getName() == null){
                startActivity(LoginActivity.class);
                return;
            }

        switch (view.getId()) {
            case R.id.me_civ:
                break;
            case R.id.tv_login_register:
                break;
            case R.id.iv_my_information:
                break;
            case R.id.iv_foods:
                break;
            case R.id.iv_upload:
                break;
        }
    }
}
