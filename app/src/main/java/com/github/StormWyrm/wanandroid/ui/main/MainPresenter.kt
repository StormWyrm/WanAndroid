package com.github.StormWyrm.wanandroid.ui.main

import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt

class MainPresenter : BasePresenterKt<MainContract.View>(),MainContract.Presenter {
    override var mModel: MainContract.Model? = MainModel()
}