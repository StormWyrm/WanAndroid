package com.github.StormWyrm.wanandroid.ui.login

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.LoadingObserver
import com.github.StormWyrm.wanandroid.bean.LoginBean
import com.orhanobut.logger.Logger

class LoginPresenter : BasePresenterKt<LoginContract.View>(), LoginContract.Presenter {
    override var mModel: LoginContract.Model? = LoginModel()

    override fun requestLogin(map: Map<String, String>) {
        RequestManager.execute(this, mModel?.requestLogin(map),
            object : LoadingObserver<LoginBean>(mView?.getContext()!!) {
                override fun onSuccess(data: LoginBean) {
                    mView?.onLoginSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    Logger.d(e)
                }
            })
    }

}
