package com.github.StormWyrm.wanandroid.ui.register

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.github.StormWyrm.wanandroid.bean.RegisterBean
import com.orhanobut.logger.Logger

class RegisterPresenter : BasePresenterKt<RegisterContract.View>(), RegisterContract.Presenter {
    override var mModel: RegisterContract.Model? = RegisterModel()

    override fun requestRegister(map: Map<String, String>) {
        RequestManager.execute(this, mModel?.requestRegister(map),
            object : LoadingObserver<RegisterBean>(mView?.getContext()!!) {
                override fun onSuccess(data: RegisterBean) {
                    mView?.onRegisterSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                }
            })
    }
}
