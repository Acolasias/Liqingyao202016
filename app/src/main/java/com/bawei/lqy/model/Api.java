package com.bawei.lqy.model;

import com.bawei.lqy.model.bean.GsonBeanLeft;
import com.bawei.lqy.model.bean.GsonBeanRight;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Time:2020/1/6 0006上午 09:05202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
public interface Api {

    @GET("category")
    Observable<GsonBeanLeft> getLeft();

    @GET("shopByCategory")
    Observable<GsonBeanRight> getRight(@Query("category") String category);
}
