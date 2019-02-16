package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.BannerBean
import com.github.StormWyrm.wanandroid.bean.article.ArticleBean
import com.orhanobut.logger.Logger

class HomePresenter : BasePresenterKt<HomeContract.View>(), HomeContract.Presenter {
    override var mModel: HomeContract.Model? = HomeModel()

    override fun requestArticleList() {
        RequestManager.execute(this, mModel?.requestArticleList(1),
            object : BaseObserver<ArticleBean>() {
                override fun onSuccess(data: ArticleBean) {
                    mView?.onRequestArticleSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                    mView?.loadDataError()
                }
            })
    }

    override fun requestBanner() {
        RequestManager.execute(this, mModel?.reqeustBanner(),
            object : BaseObserver<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>) {
                    if(data == null || data.size == 0){
                        mView?.noData()
                    }else{
//                        mView?.
                    }

                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                    mView?.loadDataError()
                }
            })
    }
}