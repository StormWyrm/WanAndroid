package com.github.StormWyrm.wanandroid.ui.tree

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean

class TreePresenter : TreeContract.Presenter, BasePresenterKt<TreeContract.View>() {
    override var mModel: TreeContract.Modle? = TreeModel()

    override fun requestTreeList() {
        RequestManager.execute(this, mModel?.requestTreeList(),
            object : BaseObserver<List<TreeBean>>(false) {
                override fun onSuccess(datas: List<TreeBean>) {
                    datas.run {
                        if (isNullOrEmpty()) {
                            mView?.noData()
                        } else {
                            mView?.showSuccess()
                            mView?.onRequestTreeListSuccess(datas)
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    mView?.loadDataError()
                }

            })
    }
}