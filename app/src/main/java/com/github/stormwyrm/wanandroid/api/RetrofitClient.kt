package com.github.stormwyrm.wanandroid.api

import com.blankj.utilcode.util.Utils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val cookiePersistor = SharedPrefsCookiePersistor(Utils.getApp())
    private val cookieCache = SetCookieCache()
    private var cookieJar = PersistentCookieJar(cookieCache, cookiePersistor)

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    fun getApiService() = retrofit.create(ApiService::class.java)

    fun clearCookie() = cookieJar.clear()

    fun isCookieEmpty() = cookiePersistor.loadAll().isEmpty()
}