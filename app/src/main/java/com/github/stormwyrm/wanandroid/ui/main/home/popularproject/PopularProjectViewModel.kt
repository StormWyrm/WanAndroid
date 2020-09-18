package com.github.stormwyrm.wanandroid.ui.main.home.popularproject

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article

class PopularProjectViewModel : BaseViewModel() {

    companion object {
        const val INITIAL_PAGE = 0
    }

    private val popularProjectRepository by lazy { PopularProjectRepository() }

    val projectList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshProjectList() {
        refreshStatus.value = true
        launch(
            block = {
                val pagination = popularProjectRepository.getTopProjectList(INITIAL_PAGE)

                page = pagination.curPage

                projectList.value = mutableListOf<Article>().apply {
                    addAll(pagination.datas)
                }

                loadStatus.value = false
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                loadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreProjectList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val pagination = popularProjectRepository.getTopProjectList(page)

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
