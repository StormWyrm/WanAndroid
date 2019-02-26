package com.github.StormWyrm.wanandroid.ui.tree.detail

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean

class TreeDetailPresenter : TreeDetailContract.Presenter, BasePresenterKt<TreeDetailContract.View>() {
    override var mModel: TreeDetailContract.Model? = TreeDetailModel()

    override fun requestTreeDetailList(pageNum: Int, cid: Int) {
        RequestManager.execute(this, mModel?.requestTreeDetailList(pageNum, cid),
            object : BaseObserver<TreeDetailBean>() {
                override fun onSuccess(data: TreeDetailBean) {
                    data.run {
                        if (size == 0 || datas == null) {
                            if (pageNum == 0) {
                                mView?.noData()
                            } else {
                                mView?.noMoreData()
                            }
                        } else {
                            mView?.onRequestTreeDetailListSuccess(datas)
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
