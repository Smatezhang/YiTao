package com.zhuoxin.zhang.yitao.medol.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public interface RetrofitApi {

    @GET("/GoodsServlet?method=/{method}")
    Call<Response> getBlogFromProduct(@Path("method") String method);

    @GET("/UserWeb?method=/{method}")
    Call<Response> getBlogFromUser(@Path("method") String method);



}
