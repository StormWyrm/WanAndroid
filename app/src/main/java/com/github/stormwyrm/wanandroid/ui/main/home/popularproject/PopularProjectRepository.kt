package com.github.stormwyrm.wanandroid.ui.main.home.popularproject

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class PopularProjectRepository {

    suspend fun getTopProjectList(page : Int) = RetrofitClient.getApiService().getTopProjectArticleList(page).apiData()

}