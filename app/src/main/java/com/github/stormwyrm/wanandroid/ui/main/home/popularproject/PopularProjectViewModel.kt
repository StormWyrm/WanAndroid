package com.github.stormwyrm.wanandroid.ui.main.home.popularproject

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.ui.main.home.popularblog.PopularBlogRepository

class PopularProjectViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private val popularProjectRepository by lazy { PopularBlogRepository() }

    val projectList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshProjectList() {
        refreshStatus.value = true
        launch(
            block = {
                val pagination = popularProjectRepository.getArticleList(INITIAL_PAGE)

                page = pagination.curPage

                projectList.value = mutableListOf<Article>().apply {
                    addAll(pagination.datas)
                }

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreProjectList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination = popularProjectRepository.getArticleList(page)

                page = pagination.curPage

                val curData = projectList.value ?: mutableListOf<Article>()

                projectList.value = curData.apply {
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
