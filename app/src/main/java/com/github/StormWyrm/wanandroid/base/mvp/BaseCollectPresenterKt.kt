package com.github.StormWyrm.wanandroid.base.mvp

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.utils.ToastUtil


abstract class BaseCollectPresenterKt<V : ICollectView<*>, M : ICollectModel> : BasePresenterKt<V>(),
    ICollectPresenter<V, M> {
    override fun requestCollect(id: Int, position: Int) {
        RequestManager.execute(this, mModel?.requestCollect(id),
            object : BaseObserver<String>(showErrorTip = false) {
                override fun onSuccess(data: String) {
                    mView?.onCollectSuccess(position)
                }

                override fun onError(e: ResponseException) {
                    if (e.getErrorCode() == (-1001).toString()) {
                        mView?.notLogin()
                    } else {
                        ToastUtil.showToast(mView?.getContext(), e.getErrorMessage())
                    }
                }
            })
    }

    override fun requestUncollect(id: Int, position: Int) {
        RequestManager.execute(this, mModel?.requestUncollect(id),
            object : BaseObserver<String>(showErrorTip = false) {
                override fun onSuccess(data: String) {
                    mView?.onUncollectSuccess(position)
                }

                override fun onError(e: ResponseException) {
                    if (e.getErrorCode() == (-1001).toString()) {
                        mView?.notLogin()
                    } else {
                        ToastUtil.showToast(mView?.getContext(), e.getErrorMessage())
                    }
                }
            })
    }

}