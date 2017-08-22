package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.view.activity.MainActivity;
import com.zhuoxin.zhang.yitao.view.base.BaseActivity;
import com.zhuoxin.zhang.yitao.presenter.RegisterPresenter;
import com.zhuoxin.zhang.yitao.view.component.ProgressDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class RegisterActivity extends BaseActivity implements IRegisterActivity {
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
    protected RegisterPresenter registerPresenter;
    protected ProgressDialogFragment progressDialogFragment;

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
        supportActionBar.setTitle("注册");
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
                    && !TextUtils.isEmpty(etUser.getText().toString().trim())
                    && !TextUtils.isEmpty(etRepassword.getText().toString().trim()));
            btnLogin.setEnabled(isEnable);
            if (isEnable) btnLogin.setBackgroundResource(R.color.colorPrimary);
        }
    };

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        if (registerPresenter == null){
            registerPresenter = new RegisterPresenter(this);
            registerPresenter.attachView();
        }
        registerPresenter.register();

    }


    @Override
    public String getUserName() {
        return etUser.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString().trim();
    }

    @Override
    public String getRePassword() {
        return etRepassword.getText().toString().trim();
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void registerSuccessed() {

        //startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void registerFailed() {

        etUser.setText("");
        etPassword.setText("");
        etRepassword.setText("");
    }

    @Override
    public void showpb() {
        if (progressDialogFragment == null){

            progressDialogFragment = new ProgressDialogFragment();
        }
        Log.e("tag","register:"+String.valueOf(progressDialogFragment.isVisible()));
        if (!progressDialogFragment.isVisible()){
            progressDialogFragment.show(getSupportFragmentManager(),"pb");
        }
    }

    @Override
    public void hidepb() {
        if (progressDialogFragment ==null){
            return;
        }
        Log.e("tag","register2:"+String.valueOf(progressDialogFragment.isVisible()));
        if (progressDialogFragment.isVisible()){

            progressDialogFragment.dismiss();
        }

    }

    @Override
    protected void onDestroy() {

        if (registerPresenter != null){
            registerPresenter.detachView();
            registerPresenter = null;
        }
        super.onDestroy();
    }
}
