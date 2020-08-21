package com.github.stormwyrm.wanandroid.api

import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.Pagination
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/article/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }
}