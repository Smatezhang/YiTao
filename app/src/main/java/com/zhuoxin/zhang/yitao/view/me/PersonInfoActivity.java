package com.zhuoxin.zhang.yitao.view.me;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.feicuiedu.apphx.model.HxUserManager;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.LoginModel;
import com.zhuoxin.zhang.yitao.medol.PersonInfoView;
import com.zhuoxin.zhang.yitao.medol.adapter.PersonAdapter;
import com.zhuoxin.zhang.yitao.medol.entity.AvaterLoadOptions;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.ItemShow;
import com.zhuoxin.zhang.yitao.medol.entity.User;
import com.zhuoxin.zhang.yitao.medol.network.EasyShopApi;
import com.zhuoxin.zhang.yitao.medol.utils.ActivityUtils;
import com.zhuoxin.zhang.yitao.presenter.PersonPresenter;
import com.zhuoxin.zhang.yitao.view.activity.MainActivity;
import com.zhuoxin.zhang.yitao.view.component.PicWindow;
import com.zhuoxin.zhang.yitao.view.component.ProgressDialogFragment;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PersonInfoActivity extends MvpActivity<PersonInfoView, PersonPresenter> implements PersonInfoView, AdapterView.OnItemClickListener {

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
    protected Unbinder mBind;
    protected ProgressDialogFragment mProgressDialogFragment;
    protected ActivityUtils mActivity;
    protected PersonAdapter mPersonAdapter;
    protected ArrayList<ItemShow> mlist;
    protected PicWindow mPicWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        mBind = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActivity = new ActivityUtils(this);
        mPersonAdapter = new PersonAdapter();
        mlist = new ArrayList<>();
        mListView.setAdapter(mPersonAdapter);
        mListView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        mlist.clear();
        User mUser = CachePreferences.getUser();
        mlist.add(new ItemShow("用户名", mUser.getName()));
        mlist.add(new ItemShow("昵称", mUser.getNick_name()));
        mlist.add(new ItemShow("环信ID", mUser.getHx_Id()));
        mPersonAdapter.add(mlist);
        updateAvatar(CachePreferences.getUser().getHead_Image());
        super.onStart();
    }

    @OnClick({R.id.iv_user_head, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
                if (mPicWindow == null) {

                    mPicWindow = new PicWindow(this, listener);
                }
                if (mPicWindow.isShowing()) {
                    mPicWindow.dismiss();
                    return;
                }
                mPicWindow.show();


                break;

            case R.id.btn_login_out:
                Intent mIntent = new Intent(this, MainActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mIntent);
                //退出环信相关
                HxUserManager.getInstance().asyncLogout();//环信退出登录
                //登出关掉通知栏中的环信通知
                EaseUI.getInstance().getNotifier().reset();


                break;
        }
    }

    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {//相册中  需要剪裁
            //从相册中清空剪裁的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //构建相册一意图  Intent.ACTION_GET_CONTENT
            Intent intent=CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);//127认为是相册
        }
        @Override
        public void toCamera() {//相机中
            //从相册中清空剪裁的缓存  MediaStore.ACTION_IMAGE_CAPTURE  uri指的是我们照完相的图片地址
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent=CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);//128认为是相机的
        }
    };
    //图片剪裁的handler
    private CropHandler cropHandler=new CropHandler() {
        //图片剪裁结束后
        //通过uri拿到图片
        @Override
        public void onPhotoCropped(Uri uri) {
            File file=new File(uri.getPath());//此为图片文件
            Log.e("tag file",file.getAbsolutePath());
            presenter.updateAvatar(file);//上传图片文件
        }
        @Override
        public void onCropCancel() {
            //停止剪裁触发
        }

        @Override
        public void onCropFailed(String message) {
            //剪裁失败
        }

        @Override
        public CropParams getCropParams() {
            //设置剪裁参数
            CropParams cropParams=new CropParams();
            //400*400的正方形 剪裁框
            cropParams.aspectX=400;
            cropParams.aspectY=400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            //底层需要上下文实例
            return PersonInfoActivity.this;
        }
    };
    //从相册中取得图片或者照完相 会回调onActivityResult

    /**
     *
     * @param requestCode  127相册  128相机
     * @param resultCode
     * @param data  图片数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler,requestCode,resultCode,data);//交给cropHandler去处理
        Log.e("tag ","------------------");
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }

    @NonNull
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    @Override
    public void showPrb() {
        if (mProgressDialogFragment == null) {

            mProgressDialogFragment = new ProgressDialogFragment();
        }
        if (mProgressDialogFragment.isVisible()) {
            return;
        }
        mProgressDialogFragment.show(getSupportFragmentManager(), "PersonInfoActivity");
    }

    @Override
    public void hidePrb() {
        mProgressDialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        mActivity.showToast(msg);
    }

    @Override
    public void updateAvatar(String url) {
        Log.e("tag url",url+"");
        if (url != null) {
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+url, mIvUserHead, AvaterLoadOptions.build_item());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mActivity.showToast(R.string.username_update);
                break;
            case 1:
                mActivity.startActivity(NickNameActivity.class);
                break;
            case 2:
                mActivity.showToast(R.string.id_update);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
