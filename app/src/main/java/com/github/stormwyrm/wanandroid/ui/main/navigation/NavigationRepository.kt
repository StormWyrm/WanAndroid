package com.github.stormwyrm.wanandroid.ui.main.navigation

import com.github.stormwyrm.wanandroid.api.RetrofitClient


class NavigationRepository {

    suspend fun getNavigationList() = RetrofitClient.getApiService().getNavigationList().apiData()

}