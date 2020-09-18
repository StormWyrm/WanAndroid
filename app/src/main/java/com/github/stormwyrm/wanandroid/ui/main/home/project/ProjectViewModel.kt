package com.github.stormwyrm.wanandroid.ui.main.home.project

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.github.stormwyrm.wanandroid.base.BaseViewModel
import com.github.stormwyrm.wanandroid.bean.Article
import com.github.stormwyrm.wanandroid.bean.LoadStatus
import com.github.stormwyrm.wanandroid.bean.ProjectCategory

class ProjectViewModel : BaseViewModel() {

    private val projectRepository by lazy {
        ProjectRepository()
    }

    val categories = MutableLiveData<MutableList<ProjectCategory>>()
    val checkedPosition: MutableLiveData<Int> = MutableLiveData()
    val articleList = MutableLiveData<MutableList<Article>>()

    val loadStatus = MutableLiveData<LoadStatus>()
    val loadListStatus = MutableLiveData<Boolean>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()

    var page = INIT_PAGE

    fun getProjectCategories() {
        loadStatus.value = LoadStatus.LOADING
        launch(
            block = {
                val categoryList = projectRepository.getProjectCategories()

                if (categoryList.isEmpty()) {
                    loadStatus.value = LoadStatus.EMPTY
                } else {
                    loadStatus.value = LoadStatus.SUCCESS
                    categories.value = categoryList

                    try {
                        loadListStatus.value = false
                        refreshStatus.value = true
                        checkedPosition.value = INIT_CHECKED

                        val cid = categoryList[INIT_CHECKED].id
                        val pagination = projectRepository.getProjectList(cid, INIT_PAGE)

                        articleList.value = pagination.datas.toMutableList()

                        page = pagination.curPage

                        if(pagination.offset >= pagination.total)
                            loadMoreStatus.value  = LoadMoreStatus.End

                        refreshStatus.value = false
                    } catch (e: Exception) {
                        loadListStatus.value = true
                        refreshStatus.value = false
                    }
                }
            },
            error = {
                loadStatus.value = LoadStatus.ERROR
            }
        )
    }

    fun refreshProjectList(checkedPosition: Int = this.checkedPosition.value ?: INIT_CHECKED) {
        refreshStatus.value = true
        loadListStatus.value = false
        launch(
            block = {
                this.checkedPosition.value = checkedPosition
                val cid = categories.value!![checkedPosition].id

                val pagination = projectRepository.getProjectList(cid, page)
                articleList.value = pagination.datas.toMutableList()

                page = pagination.curPage

                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                loadListStatus.value = page == INIT_PAGE
            }
        )
    }

    fun loadMoreProjectList() {
        loadMoreStatus.value = LoadMoreStatus.Loading
        launch(
            block = {
                val checkedCategoryIndex = checkedPosition.value ?: INIT_CHECKED
                val cid = categories.value!![checkedCategoryIndex].id

                val pagination = projectRepository.getProjectList(cid, page)
                val curArticleList = articleList.value ?: mutableListOf()
                articleList.value = curArticleList.apply { addAll(pagination.datas) }

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
        const val INIT_PAGE = 1
    }

}