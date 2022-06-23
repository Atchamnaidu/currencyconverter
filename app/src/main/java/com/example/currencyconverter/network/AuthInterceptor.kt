package com.example.currencyconverter.network

import com.example.currencyconverter.utils.Constants.Companion.APP_ID
import com.example.currencyconverter.utils.Constants.Companion.APP_ID_VAL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

// Adds default query param (app_id) to each api request
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter(APP_ID, APP_ID_VAL).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}