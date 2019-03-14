package com.github.StormWyrm.wanandroid.utils

import com.github.StormWyrm.wanandroid.base.net.interceptor.AddCookieInterceptor
import com.github.StormWyrm.wanandroid.base.net.interceptor.SaveCookieInterceptor
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitHelper<T> {
    open val timeOut: Long = 10
    val instace: T

    abstract val baseUrl: String

    init {
        val httpClient = OkHttpClient.Builder()
            .run {
                connectTimeout(timeOut, TimeUnit.SECONDS)
                readTimeout(timeOut, TimeUnit.SECONDS)
                writeTimeout(timeOut, TimeUnit.SECONDS)

                val logger = HttpLoggingInterceptor.Logger {
                    Logger.d(it)
                }
                val httpLoggingInterceptor = HttpLoggingInterceptor(logger)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(httpLoggingInterceptor)

                addInterceptor(SaveCookieInterceptor())
                addInterceptor(AddCookieInterceptor())
                build()
            }

        instace = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(getClazz())
    }

    abstract fun getClazz(): Class<T>
}