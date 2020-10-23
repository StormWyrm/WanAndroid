package com.github.stormwyrm.wanandroid.ui.main.system.pager

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article

class SystemPagerViewModel : BaseViewModel() {
    private val systemPagerRepository by lazy {
        SystemPagerRepository()
    }

    val articles = MutableLiveData<MutableList<Article>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val loadListStatus = MutableLiveData<Boolean>()

    private var curPage = 0
    private var curCid = 0

    fun refreshSystemArticle(cid: Int) {
        loadListStatus.value = false
        refreshStatus.value = true
        launch(
            block = {
                curPage = INIT_PAGE
                curCid = cid
                val pagination =
                    systemPagerRepository.getSystemArticleList(page = curPage, cid = curCid)
                articles.value = pagination.datas.toMutableList()

                refreshStatus.value = false
                curPage = pagination.curPage
            },
            error = {
                refreshStatus.value = false
                loadListStatus.value = true
            }
        )
    }

    fun loadMoreSystemArticle() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination =
                    systemPagerRepository.getSystemArticleList(page = curPage, cid = curCid)
                val curArticleList = articles.value ?: mutableListOf()
                articles.value = curArticleList.apply { addAll(pagination.datas) }

                curPage = pagination.curPage

                loadMoreStatus.value =
                    if (pagination.offset >= pagination.total) LoadMoreStatus.End else LoadMoreStatus.Complete
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.Fail
            }
        )
    }

    companion object {
        const val INIT_PAGE = 0
    }

}