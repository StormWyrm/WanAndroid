package com.github.StormWyrm.wanandroid.ui.main

import com.github.StormWyrm.wanandroid.R
import com.github.StormWyrm.wanandroid.base.activity.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>() {

    override var mPresenter: MainContract.Presenter = MainPresenter()

    override fun getLayoutId(): Int = R.layout.activity_main
}