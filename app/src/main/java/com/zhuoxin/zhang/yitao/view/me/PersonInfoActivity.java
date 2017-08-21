package com.zhuoxin.zhang.yitao.view.me;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_user_head)
    CircularImageView mIvUserHead;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.btn_login_out)
    Button mBtnLoginOut;
    @BindView(R.id.activity_person)
    RelativeLayout mActivityPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_user_head, R.id.linearLayout, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
                break;
            case R.id.linearLayout:
                break;
            case R.id.btn_login_out:
                break;
        }
    }
}
