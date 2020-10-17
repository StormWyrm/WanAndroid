package com.github.stormwyrm.wanandroid.ui.main.home.project

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class ProjectRepository {
    suspend fun getProjectCategories() =
        RetrofitClient.getApiService().getProjectCategory().apiData()

    suspend fun getProjectList(cid: Int, page: Int) =
        RetrofitClient.getApiService().getProjectArticleList(page,cid).apiData()
}