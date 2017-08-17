package com.zhuoxin.zhang.yitao.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.view.base.BaseActivity;
import com.zhuoxin.zhang.yitao.view.component.PicWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/16.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.tl)
    Toolbar tl;
    @BindView(R.id.me_civ)
    CircularImageView meCiv;
    @BindView(R.id.iv_username)
    TextView ivUsername;
    @BindView(R.id.iv_nikename)
    TextView ivNikename;
    @BindView(R.id.iv_hx_id)
    TextView ivHxId;
    @BindView(R.id.btn_back)
    Button btnBack;
    protected PicWindow picWindow;
    protected int OPPHOTO = 1;
    protected int OPENPHOTO = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(tl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.me_person_info);
        ivUsername.setText(CachePreferences.getUser().getName());
        ivHxId.setText(CachePreferences.getUser().getHx_Id());

    }

    @OnClick({R.id.me_civ, R.id.ll_user, R.id.ll_nike, R.id.ll_hx, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_civ:
                if (picWindow == null) {
                    picWindow = new PicWindow(this, new PicWindow.Listener() {
                        @Override
                        public void toGallery() {
                            pickPhoto();
                        }

                        @Override
                        public void toCamera() {
                            takePhoto();
                        }
                    });

                }
                picWindow.show();
                break;
            case R.id.ll_user:
                ivUsername.setLinksClickable(true);
                showToast(getResources().getString(R.string.username_update));
                break;
            case R.id.ll_nike:
                ivNikename.setLinksClickable(true);

                break;
            case R.id.ll_hx:
                ivHxId.setLinksClickable(true);
                showToast(getResources().getString(R.string.id_update));
                break;
            case R.id.btn_back:
                break;
        }
    }
    private void pickPhoto() {
        Intent mIntent = new Intent(Intent.ACTION_PICK, null);
        mIntent.setType("image/*");
        mIntent.putExtra("crop", "true");//设置裁剪功能
        mIntent.putExtra("aspectX", 1); //宽高比例
        mIntent.putExtra("aspectY", 1);
        mIntent.putExtra("outputX", 80); //宽高值
        mIntent.putExtra("outputY", 80);
        mIntent.putExtra("return-data", true); //返回裁剪结果
        startActivityForResult(mIntent, OPPHOTO);

    }


    private void takePhoto() {
        //打开系统拍照功能
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(mIntent, OPENPHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
