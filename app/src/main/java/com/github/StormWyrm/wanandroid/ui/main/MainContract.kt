package com.github.StormWyrm.wanandroid.ui.main

import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView

interface MainContract{
    interface Model : IModel

    interface View : IView<Presenter>

    interface Presenter : IPresenter<View,Model>
}