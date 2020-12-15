package com.github.stormwyrm.wanandroid.ui.main.discovery

import com.github.stormwyrm.wanandroid.base.BaseViewModel

class DiscoveryViewModel : BaseViewModel() {

    private val discoveryRepository by lazy {
        DiscoveryRepository()
    }


}