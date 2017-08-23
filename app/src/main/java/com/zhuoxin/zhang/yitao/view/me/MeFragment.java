package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.PersonInfoView;
import com.zhuoxin.zhang.yitao.medol.entity.AvaterLoadOptions;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopApi;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.utils.ActivityUtils;
import com.zhuoxin.zhang.yitao.view.base.BaseFragment;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.view.me.persongoods.PersonGoodsActivity;
import com.zhuoxin.zhang.yitao.view.shop.GoodsUpdateActivity;

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
    private ActivityUtils mActivityUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_message,null);
        }

        unbinder = ButterKnife.bind(this, mView);
        mActivityUtils = new ActivityUtils(getActivity());//弱引用

        init();
        return mView;
    }

    /**
     * 初始化
     * 个人信息界面
     *
     */
    private void init() {
        User user = CachePreferences.getUser();
        if (user.getName() == null) {
            return;
        } else if (user.getNick_name() == null) {
            tvLoginRegister.setText("请设置昵称");
        } else {
            tvLoginRegister.setText(user.getName());
        }
        if( user.getHead_Image()==null){
            return;
        }
        //加载头像
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + user.getHead_Image(), meCiv, AvaterLoadOptions.build_item());


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();//解绑
    }

    @OnClick({R.id.me_civ, R.id.tv_login_register, R.id.iv_my_information, R.id.iv_foods, R.id.iv_upload})
    public void onViewClicked(View view) {
        if (CachePreferences.getUser().getName() == null) {
            mActivityUtils.startActivity(LoginActivity.class);
            return;
        }

        switch (view.getId()) {
            case R.id.me_civ:
            case R.id.tv_login_register:
            case R.id.iv_my_information:
                mActivityUtils.startActivity(PersonInfoActivity.class);
                break;
            case R.id.iv_foods:
                mActivityUtils.startActivity(PersonGoodsActivity.class);

                break;
            case R.id.iv_upload:
                mActivityUtils.startActivity(GoodsUpdateActivity.class);
                break;
        }
    }

    @Override
    public void onStart() {
        init();

        super.onStart();
    }
}
