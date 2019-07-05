package com.lancer.mylibrary.myhttp.bean;

import com.james.android.library.http.HttpControl;
import com.lancer.myapplication.http.UserApi;


import com.lancer.mylibrary.myhttp.base.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserApiService {

    private UserApi mUserApi;
    private static UserApiService instance;

    private UserApiService() {
        String BASE_URL = "https://www.wanandroid.com";
        Retrofit retrofit = HttpControl.getInstance(BASE_URL).getRetrofit();
        mUserApi = retrofit.create(UserApi.class);
    }

    public static UserApiService getInstance() {
        if (instance == null) {
            instance = new UserApiService();
        }
        return instance;
    }

    public void postUser(Observer<BaseResponse<WanandroidUserBean>> observer, String username, String password, String repassword) {
        Observable observable = mUserApi.postRegisterUser(username, password, repassword);
        toSubscribe(observable, observer);
    }

    private void toSubscribe(Observable observable, Observer observer) {

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
