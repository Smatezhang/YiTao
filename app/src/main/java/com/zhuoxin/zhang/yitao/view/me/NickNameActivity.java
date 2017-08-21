package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.entity.UserResult;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopClient;
import com.zhuoxin.zhang.yitao.medol.network.UICallBack;
import com.zhuoxin.zhang.yitao.medol.utils.ActivityUtils;
import com.zhuoxin.zhang.yitao.medol.utils.RegexUtils;
import com.zhuoxin.zhang.yitao.view.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class NickNameActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_nickname)
    EditText mEtNickname;
    @BindView(R.id.tv_nickname_error)
    TextView mTvNicknameError;
    @BindView(R.id.btn_save)
    Button mBtnSave;
    protected Unbinder mBind;
    protected String mNick;
    protected ActivityUtils mActivityUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        mBind = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivityUtils = new ActivityUtils(this);
    }


    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        mNick = mEtNickname.getText().toString().trim();
        if (RegexUtils.verifyNickname(mNick)!=RegexUtils.VERIFY_SUCCESS){
            mActivityUtils.showToast(R.string.nickname_rules);
            return;
        }
        //符合要求后请求数据
        updateUser();

    }

    private void updateUser() {
        //获取user信息
        final User mUser = CachePreferences.getUser();
        mUser.setNick_name(mNick);
        Call mCall = EasyShopClient.getInstance().uploadUser(mUser);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                mActivityUtils.showToast("网络连接失败！");

            }

            @Override
            public void onResponseUI(Call call, String response) {
                UserResult mUserResult = new Gson().fromJson(response, UserResult.class);
                if (mUserResult.getCode() == 1){
                    mActivityUtils.showToast("修改成功！");
                    User mUser1 = mUserResult.getUser();
                    CachePreferences.setUser(mUser1);
                    //finish();
                    onBackPressed();

                }else mActivityUtils.showToast(mUserResult.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();

    }
}
