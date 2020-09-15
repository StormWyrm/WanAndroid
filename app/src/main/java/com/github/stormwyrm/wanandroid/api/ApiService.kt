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

    @GET("/article/listproject/{page}/json")
    suspend fun getTopProjectList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/user_article/list/{page}/json")
    suspend fun getPalazaArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }
}