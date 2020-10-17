package com.github.stormwyrm.wanandroid.ui.main.home.popularblog

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article

class PopularBlogViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private val popularBlogRepository by lazy { PopularBlogRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshArticleList() {
        refreshStatus.value = true
        launch(
            block = {
                val topArticleListDeferred = async {
                    popularBlogRepository.getTopArticleList()
                }

                val paginationDeferred = async {
                    popularBlogRepository.getArticleList(INITIAL_PAGE)
                }

                val topArticleList = topArticleListDeferred.await().apply {
                    forEach {
                        it.top = true
                    }
                }

                val pagination = paginationDeferred.await()

                page = pagination.curPage

                articleList.value = mutableListOf<Article>().apply {
                    addAll(topArticleList)
                    addAll(pagination.datas)
                }

                loadStatus.value = false
                refreshStatus.value = false
            },
            error = {
                loadStatus.value = page == INITIAL_PAGE
                refreshStatus.value = false
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination = popularBlogRepository.getArticleList(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                articleList.value = currentList.apply {
                    addAll(pagination.datas)
                }
                loadMoreStatus.value =
                    if (pagination.offset >= pagination.total) LoadMoreStatus.End else LoadMoreStatus.Complete
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.Fail
            }
        )
    }

}
