package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean

class ProjectCategoryPresenter : BasePresenterKt<ProjectCategoryContract.View>(), ProjectCategoryContract.Presenter {
    override var mModel: ProjectCategoryContract.Model? = ProjectCategoryModel()

    override fun requestProjectAricle(categoryId: Int, pageNum: Int) {
        RequestManager.execute(this, mModel?.requestProjectAricle(categoryId, pageNum),
            object : BaseObserver<ProjectBean>(false) {
                override fun onSuccess(data: ProjectBean) {
                    data.run {
                        if (size == 0) {
                            if (pageNum == 0) {
                                mView?.noData()
                            } else {
                                mView?.noMoreData()
                            }
                        } else {
                            mView?.onrequestProjectAricleSuccess(data)
                        }
                        if (over) {
                            mView?.noMoreData()
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (pageNum == 0) {
                        mView?.loadDataError()
                    } else {
                        mView?.loadMoreError()
                    }
                }

            })
    }
}