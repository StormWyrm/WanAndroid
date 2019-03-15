package com.github.StormWyrm.wanandroid.ui.collection

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.orhanobut.logger.Logger

class CollectionPresenter : BasePresenterKt<CollectionContract.View>(),
    CollectionContract.Presenter {
    override var mModel: CollectionContract.Model? = CollectionModel()

    override fun requestCollectionList(pageNum: Int) {
        RequestManager.execute(this, mModel?.requestCollectionList(pageNum),
            object : BaseObserver<ArticleBean>(false) {
                override fun onSuccess(data: ArticleBean) {
                    data.run {
                        if (datas.isNullOrEmpty()) {
                            if (pageNum == 0) {
                                mView?.noData()
                            } else {
                                mView?.noMoreData()
                            }
                        } else {
                            mView?.onRequestCollectionListSuccess(data)
                        }
                        if (over) {
                            mView?.noMoreData()
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                    if (pageNum == 0) {
                        mView?.loadDataError()
                    } else {
                        mView?.loadMoreError()
                    }
                }
            })
    }

}
