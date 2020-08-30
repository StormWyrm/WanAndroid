package com.github.stormwyrm.wanandroid.ui.main.home.popularblog

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class PopularBlogRepository {

    suspend fun getTopArticleList() = RetrofitClient.getApiService().getTopArticleList().apiData()

    suspend fun getArticleList(page: Int) = RetrofitClient.getApiService().getArticleList(page).apiData()

}