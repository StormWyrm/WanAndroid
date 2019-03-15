package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BaseCollectPresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.orhanobut.logger.Logger

class HomePresenter : BaseCollectPresenterKt<HomeContract.View, HomeContract.Model>(), HomeContract.Presenter {
    override var mModel: HomeContract.Model? = HomeModel()

    override fun requestArticleList(pageNum: Int) {
        RequestManager.execute(this, mModel?.requestArticleList(pageNum),
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
                            mView?.onRequestArticleSuccess(data)
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


    override fun requestBanner() {
        RequestManager.execute(this, mModel?.reqeustBanner(),
            object : BaseObserver<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>) {
                    if (data.isNullOrEmpty()) {
                        mView?.noData()
                    } else {
                        mView?.onRequestBannerSuccess(data)
                    }
                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                    mView?.loadDataError()
                }
            })
    }
}