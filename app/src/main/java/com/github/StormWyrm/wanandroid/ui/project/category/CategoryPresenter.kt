package com.github.StormWyrm.wanandroid.ui.project

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.project.ProjectBean

class CategoryPresenter : BasePresenterKt<CategoryContract.View>(), CategoryContract.Presenter {
    override var mModel: CategoryContract.Model? = CategoryModel()

    override fun requestProjectAricle(categoryId: Int, pageNum: Int) {
        RequestManager.execute(this, mModel?.requestProjectAricle(categoryId, pageNum),
            object : BaseObserver<ProjectBean>(false) {
                override fun onSuccess(data: ProjectBean) {
                    if (data.size == 0) {
                        if (pageNum == 0) {
                            mView?.noData()
                        } else {
                            mView?.noMoreData()
                        }
                    } else {
                        mView?.onrequestProjectAricleSuccess(data)
                    }
                    data.run {
                        if (curPage == pageCount - 1) {
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