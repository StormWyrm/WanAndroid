package com.github.stormwyrm.wanandroid.api

import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.Pagination
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }
}