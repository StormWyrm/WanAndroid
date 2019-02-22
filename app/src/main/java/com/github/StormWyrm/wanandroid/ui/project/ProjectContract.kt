package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean
import io.reactivex.Observable

interface ProjectContract {
    interface Model : IModel {
        fun getProjectCategory(): Observable<BaseResponse<List<ProjectCategoryBean>>>
    }

    interface View : IView<Presenter> {
        fun onRequestPorjectCategorySuccess(categoryBeans: List<ProjectCategoryBean>)

        fun onRequestProjectCategoryError()

        fun onRequestProjectCategoryEmpty()
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestProjectCategory()
    }
}