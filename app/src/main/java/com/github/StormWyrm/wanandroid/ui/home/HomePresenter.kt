package com.github.StormWyrm.wanandroid.ui.home

import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt

class HomePresenter : BasePresenterKt<HomeContract.View>(), HomeContract.Presenter {
    override var mModel: HomeContract.Model? = HomeModel()

    override fun requestArticleList() {
    }

    override fun requestBanner() {
    }
}