package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.mvp.*
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import io.reactivex.Observable

interface ProjectCategoryContract {
    interface Model : ICollectModel {
        fun requestProjectAricle(pageNum: Int, categoryId: Int): Observable<BaseResponse<ProjectBean>>
    }

    interface View : ICollectView<Presenter> {
        fun onrequestProjectAricleSuccess(categoryBeans: ProjectBean)
    }

    interface Presenter : ICollectPresenter<View, Model> {
        fun requestProjectAricle(pageNum: Int, categoryId: Int)
    }
}