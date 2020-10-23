package com.github.stormwyrm.wanandroid.ui.main.system.pager

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class SystemPagerRepository {

    suspend fun getSystemArticleList(page: Int, cid: Int) =
        RetrofitClient.getApiService().getSystemArticleList(page, cid).apiData()
}