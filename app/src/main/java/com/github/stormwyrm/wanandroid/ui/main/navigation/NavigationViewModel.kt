package com.github.stormwyrm.wanandroid.ui.main.navigation

import androidx.lifecycle.MutableLiveData
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.bean.Navigation

class NavigationViewModel : BaseViewModel() {
    private val navigationRepository by lazy { NavigationRepository() }

    val loadStatus = MutableLiveData<LoadStatus>()
    val navigationList = MutableLiveData<MutableList<Navigation>>()

    fun getNavigationList() {
        loadStatus.value = LoadStatus.LOADING
        launch(
            block = {
                navigationRepository.getNavigationList().run {
                    if(this.isNullOrEmpty()){
                        loadStatus.value = LoadStatus.EMPTY
                    }else{
                        navigationList.value = this
                        loadStatus.value = LoadStatus.SUCCESS
                    }
                }
            },
            error = {
                loadStatus.value = LoadStatus.ERROR
            }
        )
    }
}