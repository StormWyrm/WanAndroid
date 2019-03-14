package com.github.StormWyrm.wanandroid.ui.login

import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.LoginBean
import io.reactivex.Observable

interface LoginContract {
    interface View : IView<Presenter> {
        fun onLoginSuccess(loginBean: LoginBean)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestLogin(map: Map<String, String>)
    }

    interface Model : IModel {
        fun requestLogin(map: Map<String, String>): Observable<BaseResponse<LoginBean>>
    }
}
