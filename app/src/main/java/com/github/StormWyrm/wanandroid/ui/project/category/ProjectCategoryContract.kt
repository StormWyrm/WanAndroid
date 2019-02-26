package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import io.reactivex.Observable

interface ProjectCategoryContract {
    interface Model : IModel {
        fun requestProjectAricle(pageNum: Int, categoryId: Int): Observable<BaseResponse<ProjectBean>>
    }

    interface View : IListView<Presenter> {
        fun onrequestProjectAricleSuccess(categoryBeans: ProjectBean)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestProjectAricle(pageNum: Int, categoryId: Int)
    }
}