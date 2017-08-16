package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.LoginModel;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopApi;
import com.zhuoxin.zhang.yitao.medol.user.User;
import com.zhuoxin.zhang.yitao.view.base.BaseFragment;
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

        init();
        return mView;
    }

    private void init() {
        User user = CachePreferences.getUser();
        if (user.getName() !=null){
            //Log.e("tag", user.toString());
            if (user.getHead_Image() != null){
                Glide.with(this).load(EasyShopApi.IMAGE_URL+user.getHead_Image()).into(meCiv);
            }
           if (user.getNick_name()!=null){
               tvLoginRegister.setText(user.getName());
           }else {
               tvLoginRegister.setText("请设置昵称");
           }


        }

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
                startActivity(UserInfoActivity.class);

                break;
            case R.id.tv_login_register:
                startActivity(UserInfoActivity.class);

                break;
            case R.id.iv_my_information:
                startActivity(UserInfoActivity.class);

                break;
            case R.id.iv_foods:


                break;
            case R.id.iv_upload:


                break;
        }
    }

    @Override
    public void onResume() {
        init();

        super.onResume();
    }
}
