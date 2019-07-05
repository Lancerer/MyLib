package com.lancer.serviceandreceiver.myhttp.Interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookiesInterceptor(context: Context) : Interceptor {
    private val context: Context

    init {
        this.context = context
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE)
        return chain.proceed(builder.build())

    }

}