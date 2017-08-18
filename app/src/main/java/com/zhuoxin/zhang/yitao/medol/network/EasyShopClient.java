package com.zhuoxin.zhang.yitao.medol.network;

import com.google.gson.Gson;
import com.zhuoxin.zhang.yitao.medol.entity.CachePreferences;
import com.zhuoxin.zhang.yitao.medol.entity.GoodsUpLoad;
import com.zhuoxin.zhang.yitao.medol.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/15.
 */

public class EasyShopClient {
    private static EasyShopClient mEasyShopClient;
    private OkHttpClient mClient;
    private Gson mGson;
    protected Retrofit mRetrofit;

    public static EasyShopClient getInstance(){
        if (mEasyShopClient == null){
            synchronized (EasyShopClient.class){
                if (mEasyShopClient == null){
                    mEasyShopClient = new EasyShopClient();
                }
            }
        }
        return mEasyShopClient;
    }

    /**
     * 网络请求客户端
     */
    private  EasyShopClient(){
        //日志拦截器
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
        mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .addInterceptor(mHttpLoggingInterceptor)
                .build();
        mGson = new Gson();
    }
    private  EasyShopClient(int i){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(EasyShopApi.BASE_URL)
                .build();

    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Call login(String username,String password){
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .post(body)
                .build();

        return mClient.newCall(request);
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    public Call register(String username,String password){
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .post(body)
                .build();

        return mClient.newCall(request);
    }

    /**
     * 加载全部商品
     * @param page
     * @param type
     * @return
     */

    public Call loadGoods(int page,String type){
        FormBody body = new FormBody.Builder()
                .add("pageNo", ""+page)
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(body)
                .build();

        return mClient.newCall(request);

    }
    //上传头像
    public Call uploadAvatar(File file){//上传图片文件
        RequestBody requestBody=new MultipartBody.Builder()
                //上传文件的方式
                .setType(MultipartBody.FORM)//multipart/form-data
                //需要一个用户类JSON字符串
                .addFormDataPart("user",mGson.toJson(CachePreferences.getUser()))
                //"image"：key值   file.getName():文件名  RequestBody.create(MediaType.parse("image/png"),file)要上传的文件和文件类型
                .addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file))
                .build();
        //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //修改昵称
    public Call uploadUser(User user) {
        //构建请求体
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//multipart/form-data
                //传入一个用户的json字符串
                .addFormDataPart("user",mGson.toJson(user))
                .build();
        //构建请求
        Request request=new Request.Builder()
                //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //获取个人商品
    //商品每页20条数据
    //pageNo=1 //string
    //发布者
    //master="android"
    //类型
    //type="dress"    //空字符串可获取所有个人商品
    public Call getPersonData(int pageNo, String type, String master) {
        RequestBody requestBody=new FormBody.Builder()
                .add("pageNo",String.valueOf(pageNo))
                .add("master",master)
                .add("type",type)
                .build();
        Request request=new Request.Builder()
                //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll
                .url(EasyShopApi.BASE_URL+EasyShopApi.GETGOODS)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //删除商品
    /*
            路径： GoodsServlet?method=delete
            方法： post
            请求：
            //商品的uuid(商品表中的主键)
            uuid="....."
     */
    public Call deleteGoods(String uuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",uuid)
                .build();
//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=delete
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DELETE)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //上传商品
    /*
    路径： GoodsServlet?method=add
    方法： post/Multipart
    请求：
    //请求是一个商品类的JSON字符串， 商品的图片以文件形式上传
    {
        "description": "诚信商家，非诚勿扰",     //商品描述
        "master": "android",                    //商品发布者
        "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
        "price": "88",                          //商品价格
        "type": "gift"                          //商品类型
    }
     */
    //files表示多个图片的文件
    public Call upLoad(GoodsUpLoad goodsUpLoad, ArrayList<File> files){
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good",mGson.toJson(goodsUpLoad));
        //将所有图片添加进来
        for (File file:files){//将图片一张一张添加到请求体中
            builder.addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
        }
        RequestBody requestBody=builder.build();//构建请求体
        //构建请求
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPLOADGOODS)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //获取好友列表
    /*
    路径： UserWeb?method=getNames
    方法： post
    请求：
    //表单
    name=[环信id1,环信id2,....]
     */
    //ids 环信ID集合
    public Call getUsers(List<String> ids){
        String names=ids.toString();//"[1 2 3 4]"
        //去空格
        names=names.replace(" ","");//[1234]
        RequestBody requestBody=new FormBody.Builder()
                .add("name",names)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_NAMES)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }
    //根据用户昵称查询好友
    /*
    路径： UserWeb?method=getUsers
    方法： post
    请求：
    //表单
    nickname=用户昵称
     */
    public Call getSearchUser(String nickname){
        RequestBody requestBody=new FormBody.Builder()
                .add("nickname",nickname)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_USER)
                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }

    public Call getGoodsData(String goodsUuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",goodsUuid)//uuid=5606FF8EF60146A48F1FCDC34144907D
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DETAIL)//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=view

                .post(requestBody)
                .build();
        return mClient.newCall(request);
    }

}
