package com.zhuoxin.zhang.yitao.medol.network;

import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/8/15.
 */

public interface NetService {
    @GET("blog/{method}")
    Call<Response> getBlog(@Path("method") String method );

}
