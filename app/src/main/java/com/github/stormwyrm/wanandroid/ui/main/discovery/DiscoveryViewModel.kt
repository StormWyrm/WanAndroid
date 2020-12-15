package com.github.stormwyrm.wanandroid.ui.main.discovery

import androidx.lifecycle.MutableLiveData
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Banner
import com.github.stormwyrm.wanandroid.bean.FrequentlyWebsite
import com.github.stormwyrm.wanandroid.bean.HotWord
import com.github.stormwyrm.wanandroid.bean.LoadStatus

class DiscoveryViewModel : BaseViewModel() {

    private val discoveryRepository by lazy {
        DiscoveryRepository()
    }

    val banners = MutableLiveData<MutableList<Banner>>()
    val hotWords = MutableLiveData<MutableList<HotWord>>()
    val frequentlyWebsites = MutableLiveData<MutableList<FrequentlyWebsite>>()
    val loadStatus = MutableLiveData<LoadStatus>()

    fun getData() {
        loadStatus.value = LoadStatus.LOADING
        launch(
            block = {
                banners.value = discoveryRepository.getBannerList()
                hotWords.value = discoveryRepository.getHotWordList()
                frequentlyWebsites.value = discoveryRepository.getFrequentlyWebsiteList()
                loadStatus.value = LoadStatus.SUCCESS
            },
            error = {
                loadStatus.value = LoadStatus.ERROR
            }
        )
    }

}