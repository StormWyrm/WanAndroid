package com.github.stormwyrm.wanandroid.ui.main.home.palaza

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.Pagination

class PalazaViewModel : BaseViewModel() {

    private val palazaRepository by lazy { PalazaRepository() }
    private var page = INIT_PAGE

    val articleList = MutableLiveData<MutableList<Article>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    fun refreshArticleList() {
        refreshStatus.value = true
        launch(
            block = {
                val pagination = palazaRepository.getPalazaArticleList(INIT_PAGE)

                articleList.value = mutableListOf<Article>().apply {
                    addAll(pagination.datas)
                }

                page = pagination.curPage
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination = palazaRepository.getPalazaArticleList(INIT_PAGE)

                val curArticleList = articleList.value ?: mutableListOf()

                articleList.value = curArticleList.apply {
                    addAll(pagination.datas)
                }

                page = pagination.curPage

                if(pagination.offset < pagination.total)
                    loadMoreStatus.value = LoadMoreStatus.Complete
                else
                    loadMoreStatus.value = LoadMoreStatus.End
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