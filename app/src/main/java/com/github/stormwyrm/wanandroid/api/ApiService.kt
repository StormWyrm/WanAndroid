package com.github.stormwyrm.wanandroid.api

import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.bean.Pagination
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>

    @GET("/article/listproject/{page}/json")
    suspend fun getTopProjectArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/user_article/list/{page}/json")
    suspend fun getPalazaArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/project/tree/json")
    suspend fun getProjectCategory(): ApiResult<MutableList<Category>>

    @GET("/project/list/{page}/json")
    suspend fun getProjectArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>

    @GET("/wxarticle/chapters/json")
    suspend fun getWechatCategory(): ApiResult<MutableList<Category>>

    @GET("/wxarticle/list/{cid}/{page}/json")
    suspend fun getWechatArticleList(
        @Path("cid") cid: Int,
        @Path("page") page: Int
    ): ApiResult<Pagination<Article>>

    @GET("/tree/json")
    suspend fun getSystemCategory(): ApiResult<MutableList<Category>>

    @GET("/article/list/{page}/json")
    suspend fun getSystemArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): ApiResult<Pagination<Article>>

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }
}