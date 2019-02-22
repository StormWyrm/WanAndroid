package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.project.ProjectCategoryBean

class ProjectPresenter : BasePresenterKt<ProjectContract.View>(), ProjectContract.Presenter {
    override var mModel: ProjectContract.Model? = ProjectModel()

    override fun requestProjectCategory() {
        RequestManager.execute(this, mModel?.getProjectCategory(),
            object : BaseObserver<List<ProjectCategoryBean>>(false) {
                override fun onSuccess(data: List<ProjectCategoryBean>) {
                    if (data.isEmpty()) {
                        mView?.onRequestProjectCategoryEmpty()
                    } else {
                        mView?.onRequestPorjectCategorySuccess(data)
                    }
                }

                override fun onError(e: ResponseException) {
                    mView?.onRequestProjectCategoryError()
                }

            })
    }
}