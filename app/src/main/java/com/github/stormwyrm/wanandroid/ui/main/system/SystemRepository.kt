package com.github.stormwyrm.wanandroid.ui.main.system

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class SystemRepository {

    suspend fun getSystemCategory() = RetrofitClient.getApiService().getSystemCategory().apiData()

}