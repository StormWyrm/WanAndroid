package com.github.StormWyrm.wanandroid.ui.register

import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.RegisterBean
import io.reactivex.Observable

interface RegisterContract {
    interface View : IView<Presenter> {
        fun onRegisterSuccess(registerBean: RegisterBean)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestRegister(map: Map<String, String>)
    }

    interface Model : IModel {
        fun requestRegister(map: Map<String, String>): Observable<BaseResponse<RegisterBean>>
    }
}
