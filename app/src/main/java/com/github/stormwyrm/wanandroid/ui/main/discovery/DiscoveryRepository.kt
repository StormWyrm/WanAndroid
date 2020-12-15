package com.github.stormwyrm.wanandroid.ui.main.discovery

import com.github.stormwyrm.wanandroid.api.RetrofitClient

class DiscoveryRepository {

    suspend fun getBannerList() = RetrofitClient.getApiService().getBannerList().apiData()

    suspend fun getHotWordList() = RetrofitClient.getApiService().getHotWordList().apiData()

    suspend fun getFrequentlyWebsiteList() =
        RetrofitClient.getApiService().getFrequentlyWebsiteList().apiData()

}