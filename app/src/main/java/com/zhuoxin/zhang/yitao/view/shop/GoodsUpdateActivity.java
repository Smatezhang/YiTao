package com.zhuoxin.zhang.yitao.view.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.zhang.yitao.R;
import com.zhuoxin.zhang.yitao.medol.GoodsupdateView;
import com.zhuoxin.zhang.yitao.medol.adapter.GoodsUploadAdapter;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsUpLoad;
import com.zhuoxin.zhang.yitao.medol.entity.ImageItem;
import com.zhuoxin.zhang.yitao.medol.utils.ActivityUtils;
import com.zhuoxin.zhang.yitao.medol.utils.ImageUtils;
import com.zhuoxin.zhang.yitao.medol.utils.MyFileUtils;
import com.zhuoxin.zhang.yitao.presenter.GoodsUploadPresenter;
import com.zhuoxin.zhang.yitao.view.component.PicWindow;
import com.zhuoxin.zhang.yitao.view.component.ProgressDialogFragment;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GoodsUpdateActivity extends MvpActivity<GoodsupdateView, GoodsUploadPresenter> implements GoodsupdateView {
    @BindView(R.id.tv_goods_delete)
    TextView mTvGoodsDelete;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_goods_name)
    EditText mEtGoodsName;
    @BindView(R.id.et_goods_price)
    EditText mEtGoodsPrice;
    @BindView(R.id.tv_goods_type)
    TextView mTvGoodsType;
    @BindView(R.id.btn_goods_type)
    Button mBtnGoodsType;
    @BindView(R.id.et_goods_describe)
    EditText mEtGoodsDescribe;
    @BindView(R.id.btn_goods_load)
    Button mBtnGoodsLoad;
    @BindView(R.id.activity_goods_up_load)
    RelativeLayout mActivityGoodsUpLoad;
    protected Unbinder mBind;
    protected ActivityUtils mActivity;
    protected ProgressDialogFragment mProgressDialogFragment;
    protected PicWindow mPicWindow;
    protected GoodsUploadAdapter mGoodsUploadAdapter;
    //定义数据
    private final String[] goods_type = {"家用", "电子", "服饰", "玩具", "图书", "礼品", "其他"};
    //定义商品的类型
    private final String[] goods_type_num = {"household", "electron", "dress", "toy", "book", "gift", "other"};


    private String str_goods_name;//获取商品名
    private String str_goods_price;//商品价格
    private String str_goods_type = goods_type_num[0];//商品类型默认家用
    private String str_goods_describe;//商品描述
    //模式：普通 模式
    private static final int MODE_DONE = 1;//点击删除的模式 没有ChechBox的模式
    //模式：删除 模式
    private static final int MODE_DELETE = 2;//长按  有CheckBox的模式
    //存储当前模式
    private int title_mode = MODE_DONE;//一来默认是没有ChechBox的模式
    protected List<ImageItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_up_load);
        mBind = ButterKnife.bind(this);
        mActivity = new ActivityUtils(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init() {
        mPicWindow = new PicWindow(this, listener);
        mGoodsUploadAdapter = new GoodsUploadAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);//提高性能
        mRecyclerView.setAdapter(mGoodsUploadAdapter);
        mGoodsUploadAdapter.setListener(itemClickListener);
        //给编辑框设置监听
        mEtGoodsName.addTextChangedListener(textWatcher);//商品名称监听
        mEtGoodsDescribe.addTextChangedListener(textWatcher);//商品价格监听
        mEtGoodsPrice.addTextChangedListener(textWatcher);//商品描述监听

    }

    private GoodsUploadAdapter.OnItemClickListener itemClickListener = new GoodsUploadAdapter.OnItemClickListener() {
        @Override
        public void onAddClicked() {//点击加号图片弹出PopupWindow
            if (mPicWindow != null && mPicWindow.isShowing()) {//PopupWindow显示点击加号  消失
                mPicWindow.dismiss();
            } else if (mPicWindow != null) {
                mPicWindow.show();////PopupWindow显示点击加号  显示
            }
        }

        @Override
        public void onPhotoClicked(ImageItem imageItem) {//点击商品图片进入图片详情页
            //点击商品上传图片跳转到商品详情页面
            Intent intent = new Intent(GoodsUpdateActivity.this, GoodsUploadImageShowActivity.class);
            intent.putExtra("images", imageItem.getBitmap());//Bitmap实现了Parcelable的序列化接口
            startActivity(intent);//启动意图
        }

        @Override
        public void onLongClicked() {//长按 显示删除按钮  (适配器里显示CheckBox)
            //模式变成可删除模式  出现删除按钮
            title_mode = MODE_DELETE;
            //删除按钮显示
            mTvGoodsDelete.setVisibility(View.VISIBLE);
        }
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {//监听编辑框改变之后的操作
            str_goods_name = mEtGoodsName.getText().toString();//获取商品名称
            str_goods_price = mEtGoodsPrice.getText().toString();//获取商品的价格
            str_goods_describe = mEtGoodsDescribe.getText().toString();//获取商品描述
            //所有的编辑框都有内容时才可以上传
            boolean can_upload = !(TextUtils.isEmpty(str_goods_name) || TextUtils.isEmpty(str_goods_price) || TextUtils.isEmpty(str_goods_describe));
            mBtnGoodsLoad.setEnabled(can_upload);
        }
    };

    //点击删除 按钮
    private void changeModeActivity() {
        //判断 当前模式
        if (mGoodsUploadAdapter.getMode() == GoodsUploadAdapter.MADE_MUTI_SELECT) {//显示checkBox的状态
            mTvGoodsDelete.setVisibility(View.GONE);//删除按钮修饰
            //更改模式
            title_mode = MODE_DONE;//不显示CheckBox的模式
            //更改CheckBox中的模式
            mGoodsUploadAdapter.changeMode(GoodsUploadAdapter.MADE_NOMAL);//不显示CheckBox的模式
            //可写可不写
            for (int i = 0; i < mGoodsUploadAdapter.getList().size(); i++) {//遍历的是删除过留下来的元素
                mGoodsUploadAdapter.getList().get(i).setCheck(false);
            }
        }
    }

    private PicWindow.Listener listener = new PicWindow.Listener() {
        @Override
        public void toGallery() {//获取相册图片
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //Intent.ACTION_GET_CONTENT
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            //启动相册组件
            startActivityForResult(intent, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {//拍照获取图片
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //MediaStore.ACTION_IMAGE_CAPTURE
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            //启动相机组件
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };

    //剪裁图片的Handler
    public CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {//图片剪裁完成后
            //把我们剪裁的图片保存到bitmap中,并保存到sd中
            //文件名：用系统当前时间  不重复
            //4393726151119.jpeg
            String fileName = String.valueOf(System.currentTimeMillis());
            //先变成Bitmap
            Bitmap bitmap = ImageUtils.readDownsampledImage(uri.getPath(), 1080, 1920);
            //将图片存储到sd卡中
            MyFileUtils.saveBitmap(bitmap, fileName);//照完相  将图片保存到外置sd卡中
            //展示出来
            ImageItem imageItem = new ImageItem();
            imageItem.setBitmap(bitmap);//放置bitmap图片
            imageItem.setImagePath(fileName + ".JPEG");//放置文件名
            mGoodsUploadAdapter.add(imageItem);//添加一个上传商品
            mGoodsUploadAdapter.notifyDataSetChanged();//更新适配器
        }

        @Override
        public void onCropCancel() {//剪裁取消

        }

        @Override
        public void onCropFailed(String message) {//剪裁失败

        }

        @Override
        public CropParams getCropParams() {//设置剪裁参数
            CropParams params = new CropParams();
            //设置剪裁框
            params.aspectX = 400;
            params.aspectY = 400;
            return params;
        }

        @Override
        public Activity getContext() {
            return GoodsUpdateActivity.this;
        }
    };

    //获得图片后执行  data存储了我们的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //将图片data交给cropHandler处理
        CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (title_mode == MODE_DONE) {//无CheckBox的状态
            //删除缓存
            deleteCache();
            finish();
        } else if (title_mode == MODE_DELETE) {//当前状态是 有CheckBox的状态
            //changeModeActivity();
        }

    }

    private void deleteCache() {
        for (int i = 0; i < mGoodsUploadAdapter.getList().size(); i++) {
            MyFileUtils.delFile(mGoodsUploadAdapter.getList().get(i).getImagePath());//获取每一张图片 删除
        }
    }

    @NonNull
    @Override
    public GoodsUploadPresenter createPresenter() {
        return new GoodsUploadPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @Override
    public void showPrb() {//显示progressbar
        if (mProgressDialogFragment == null) {
            mProgressDialogFragment = new ProgressDialogFragment();
        }
        if (mProgressDialogFragment.isVisible()) {
            return;
        }
        mProgressDialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
    }

    @Override
    public void hidePrb() {
        mProgressDialogFragment.dismiss();
    }

    @Override
    public void upLoadSuccess() {
        finish();
    }

    @Override
    public void showMsg(String msg) {
        mActivity.showToast(msg);
    }


    /*@OnClick({R.id.tv_goods_delete, R.id.btn_goods_type, R.id.btn_goods_load})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                List<ImageItem> mList = mGoodsUploadAdapter.getList();
                for (int i = mList.size() - 1; i >= 0; i--) {
                    if (mList.get(i).isCheck()) {
                        MyFileUtils.delFile(mList.get(i).getImagePath());
                        mList.remove(i);
                    }
                }
                list = mList;

                changeModeActivity();
                break;
            case R.id.btn_goods_type:

                AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
                mDialog.setItems(goods_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvGoodsType .setText(goods_type[which]);
                        str_goods_type = goods_type_num[which];
                    }
                });
                mDialog.create().show();
                break;
            case R.id.btn_goods_load:
                if (list.size()==0){
                    mActivity.showToast("最少有一张商品图片");
                    return;
                }
                Log.e("onViewClicked: ","------------" );
                presenter.upLoad(setGoodsInfo(),list);
                break;
        }
    }*/
    private GoodsUpLoad setGoodsInfo() {
        GoodsUpLoad goodsUpLoad = new GoodsUpLoad();
        goodsUpLoad.setName(str_goods_name);//上传商品名称
        goodsUpLoad.setPrice(str_goods_price);//上传商品价格
        goodsUpLoad.setDescription(str_goods_describe);//上传商品描述
        goodsUpLoad.setType(str_goods_type);//上传商品类型
        goodsUpLoad.setMaster(CachePreferences.getUser().getName());//撒谎上传发布者
        return goodsUpLoad;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_goods_delete, R.id.btn_goods_type, R.id.btn_goods_load})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                List<ImageItem> mList = mGoodsUploadAdapter.getList();
                for (int i = mList.size() - 1; i >= 0; i--) {
                    if (mList.get(i).isCheck()) {
                        MyFileUtils.delFile(mList.get(i).getImagePath());
                        mList.remove(i);
                    }
                }

                list.addAll(mList);

                changeModeActivity();
                break;
            case R.id.btn_goods_type:
                AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
                mDialog.setItems(goods_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvGoodsType .setText(goods_type[which]);
                        str_goods_type = goods_type_num[which];
                    }
                });
                mDialog.create().show();
                break;

            case R.id.btn_goods_load:
                if (mGoodsUploadAdapter.getItemCount()-1==0){
                    mActivity.showToast("最少有一张商品图片");
                    return;
                }
                Log.e("onViewClicked: ","------------" );
                presenter.upLoad(setGoodsInfo(),list);

                break;
        }
    }
}
