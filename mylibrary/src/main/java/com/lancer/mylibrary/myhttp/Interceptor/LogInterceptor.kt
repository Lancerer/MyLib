package com.lancer.serviceandreceiver.myhttp.Interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class LogInterceptor : Interceptor {
    private val TAG = LogInterceptor::class.java.simpleName
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(TAG, "request:" + request.toString())
        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request());
        val t2 = System.nanoTime();
        Log.d(
            TAG, String.format(
                Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        val mediaType = response.body()?.contentType();
        val content = response.body()?.string()
        Log.d(TAG, "response body:" + content)
        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content))
            .build()
    }
}