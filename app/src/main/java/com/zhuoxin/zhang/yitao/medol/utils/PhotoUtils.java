package com.zhuoxin.zhang.yitao.medol.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;



/**
 * Created by Administrator on 2017/8/16.
 */

public class PhotoUtils {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void pickPhoto() {
        Intent mIntent = new Intent(Intent.ACTION_PICK, null);
        mIntent.setType("image/*");
        mIntent.putExtra("crop", "true");//设置裁剪功能
        mIntent.putExtra("aspectX", 1); //宽高比例
        mIntent.putExtra("aspectY", 1);
        mIntent.putExtra("outputX", 80); //宽高值
        mIntent.putExtra("outputY", 80);
        mIntent.putExtra("return-data", true); //返回裁剪结果
        //startActivityForResult(mIntent, OPPHOTO);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void takePhoto() {
        //打开系统拍照功能
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(mIntent, OPENPHOTO);

    }
}
