package com.github.StormWyrm.wanandroid.utils

import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitHelper<T> {
    open val timeOut: Long = 15
    val instace: T

    abstract val baseUrl: String

    init {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor {
                val builder = it.request().newBuilder()

                it.proceed(builder.build())
            }.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Logger.d(it)
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .build()

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