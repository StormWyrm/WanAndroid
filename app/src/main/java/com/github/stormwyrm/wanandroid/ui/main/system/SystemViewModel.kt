package com.github.stormwyrm.wanandroid.ui.main.system

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.bean.LoadStatus

class SystemViewModel : BaseViewModel(){

    private val systemRepository by lazy {
        SystemRepository()
    }

    val loadStatus = MutableLiveData<LoadStatus>()
    val categories = MutableLiveData<MutableList<Category>>()

    fun loadSystemCategory(){
        loadStatus.value = LoadStatus.LOADING
        launch(
            block = {
                val categoryList = systemRepository.getSystemCategory()
                if(categoryList.isNullOrEmpty()){
                    loadStatus.value = LoadStatus.EMPTY
                }else{
                    loadStatus.value = LoadStatus.SUCCESS
                    categories.value = categoryList.toMutableList()
                }
            },
            error = {
                loadStatus.value = LoadStatus.ERROR
            }
        )
    }
}