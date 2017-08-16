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

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tl)
    Toolbar tl;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        setSupportActionBar(tl);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.register);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        //supportActionBar.setHomeButtonEnabled(true);
        etUser.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etRepassword.addTextChangedListener(textWatcher);
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
            Boolean isEnable = (!TextUtils.isEmpty(etPassword.getText().toString().trim())
                    &&!TextUtils.isEmpty(etUser.getText().toString().trim())
                    &&!TextUtils.isEmpty(etRepassword.getText().toString().trim()));
            btnLogin.setEnabled(isEnable);
            if (isEnable) btnLogin.setBackgroundResource(R.color.colorPrimary);
        }
    };

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
    }
}
