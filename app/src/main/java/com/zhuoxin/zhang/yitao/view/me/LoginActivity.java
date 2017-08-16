package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.view.base.BaseActivity;
import com.zhuoxin.zhang.yitao.medol.user.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.user.UserResult;
import com.zhuoxin.zhang.yitao.presenter.LoginPresenter;
import com.zhuoxin.zhang.yitao.view.activity.MainActivity;
import com.zhuoxin.zhang.yitao.view.component.ProgressDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/15.
 */

public class LoginActivity extends BaseActivity implements ILoginActivity {
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_fast_register)
    TextView tvFastRegister;
    @BindView(R.id.tl)
    Toolbar tl;
    protected ProgressDialogFragment progressDialogFragment;
    protected Unbinder bind;
    protected LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        bind = ButterKnife.bind(this);
        init();

    }

    private void init() {

        setSupportActionBar(tl);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.login);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        //supportActionBar.setHomeButtonEnabled(true);
        etUser.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        tl.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Boolean isEnable = (!TextUtils.isEmpty(etPassword.getText().toString().trim()) && !TextUtils.isEmpty(etUser.getText().toString().trim()));
            btnLogin.setEnabled(isEnable);
            if (isEnable) btnLogin.setBackgroundResource(R.drawable.selector_btn);
        }
    };


    @OnClick({R.id.btn_login, R.id.tv_fast_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (loginPresenter == null) {
                    loginPresenter = new LoginPresenter(this);
                }
                loginPresenter.login();

                break;
            case R.id.tv_fast_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public String getpassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getusername() {
        return etUser.getText().toString().trim();

    }


    @Override
    public void loginSuccessed(UserResult result) {
        showToast(result.getMessage());
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void loginFailed(String s) {
        showToast(s);

    }

    @Override
    public void showpb() {
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (!progressDialogFragment.isVisible()) {
            progressDialogFragment.show(getSupportFragmentManager(), "pb");
        }
    }

    @Override
    public void hidepb() {
        if (progressDialogFragment != null) {
            if (progressDialogFragment.isVisible()) progressDialogFragment.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (progressDialogFragment != null) {
            progressDialogFragment = null;
        }
        bind.unbind();

        super.onDestroy();
    }
}
