package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean
import com.github.StormWyrm.wanandroid.bean.project.ProjectDataItem
import io.reactivex.Observable

interface CategoryContract {
    interface Model : IModel {
        fun requestProjectAricle(categoryId: Int, pageNum: Int): Observable<BaseResponse<ProjectBean>>
    }

    interface View : IListView<Presenter> {
        fun onrequestProjectAricleSuccess(categoryBeans: ProjectBean)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestProjectAricle(categoryId: Int, pageNum: Int)
    }
}