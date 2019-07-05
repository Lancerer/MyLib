package com.lancer.myapplication.http

import com.lancer.mylibrary.myhttp.base.BaseResponse
import com.lancer.mylibrary.myhttp.bean.WanandroidUserBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//https://www.wanandroid.com/user/register
interface UserApi {
    @POST("/user/register")
    @FormUrlEncoded
    fun postRegisterUser(@Field("username") username: String,
                         @Field("password") password: String,
                         @Field("repassword") repassword: String): Observable<BaseResponse<WanandroidUserBean>>
}
