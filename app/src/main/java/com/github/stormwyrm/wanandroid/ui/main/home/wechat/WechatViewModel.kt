package com.github.stormwyrm.wanandroid.ui.main.home.wechat

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.Category
import com.github.stormwyrm.wanandroid.bean.LoadStatus

class WechatViewModel : BaseViewModel() {

    private val wechatRepository by lazy {
        WechatRepository()
    }

    val categories = MutableLiveData<MutableList<Category>>()
    val checkedPosition = MutableLiveData<Int>()
    val articles = MutableLiveData<MutableList<Article>>()
    val loadStatus = MutableLiveData<LoadStatus>()
    val loadListStatus = MutableLiveData<Boolean>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    var page = INIT_PAGE

    fun getWechatCategory() {
        loadStatus.value = LoadStatus.LOADING
        launch(
            block = {
                val categoryList = wechatRepository.getWechatCategory()
                if (categoryList.isEmpty())
                    loadStatus.value = LoadStatus.EMPTY
                else {
                    categories.value = categoryList
                    loadStatus.value = LoadStatus.SUCCESS

                    refreshWechatList()
                }

            },
            error = {
                loadStatus.value = LoadStatus.ERROR
            }
        )
    }

    fun refreshWechatList(checkedPosition: Int = this.checkedPosition.value ?: INIT_CHECKED) {
        loadListStatus.value = false
        refreshStatus.value = true
        launch(
            block = {
                this.checkedPosition.value = checkedPosition
                val cid = categories.value!![checkedPosition].id

                val pagination = wechatRepository.getWechatList(cid, INIT_PAGE)
                articles.value = pagination.datas.toMutableList()

                page = pagination.curPage
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                loadListStatus.value = true
            }
        )
    }

    fun loadMoreWechatList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val checkedCategoryIndex = checkedPosition.value ?: INIT_CHECKED
                val cid = categories.value!![checkedCategoryIndex].id

                val pagination = wechatRepository.getWechatList(cid, page)
                val curArticleList = articles.value ?: mutableListOf()
                articles.value = curArticleList.apply { addAll(pagination.datas) }

                page = pagination.curPage

                loadMoreStatus.value =
                    if (pagination.offset >= pagination.total) LoadMoreStatus.End else LoadMoreStatus.Complete

            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.Fail
            }
        )
    }

    companion object {
        const val INIT_CHECKED = 0
        const val INIT_PAGE = 0
    }
}