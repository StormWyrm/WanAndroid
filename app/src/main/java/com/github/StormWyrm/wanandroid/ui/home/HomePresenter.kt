package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.convert.ExceptionConvert
import com.github.StormWyrm.wanandroid.base.net.convert.ResponseConvert
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.BannerBean

class HomePresenter : BasePresenterKt<HomeContract.View>(), HomeContract.Presenter {
    override var mModel: HomeContract.Model? = HomeModel()

    override fun requestArticleList() {
    }

    override fun requestBanner() {
        mModel?.reqeustBanner()
            ?.map(ResponseConvert())
            ?.onErrorResumeNext(ExceptionConvert<List<BannerBean>>())
            ?.subscribe(object : BaseObserver<List<BannerBean>>() {
                override fun onSuccess(data: List<BannerBean>) {
                }

                override fun onError(e: ResponseException) {
                }

            })
    }
}