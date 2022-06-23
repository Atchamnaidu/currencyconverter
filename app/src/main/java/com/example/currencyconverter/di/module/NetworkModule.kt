package com.example.currencyconverter.di.module

import android.content.Context
import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.network.ApiServices
import com.example.currencyconverter.network.AuthInterceptor
import com.example.currencyconverter.network.NetworkConnectionInterceptor
import com.example.currencyconverter.utils.Constants.Companion.BASE_URL
import com.example.currencyconverter.utils.Constants.Companion.CON_TIMEOUT
import com.example.currencyconverter.utils.Constants.Companion.READ_TIMEOUT
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(AuthInterceptor())
            .connectTimeout(CON_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)


        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(httpLoggingInterceptor)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesApiClient(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }
}