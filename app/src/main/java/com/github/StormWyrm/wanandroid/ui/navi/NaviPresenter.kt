package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean

class NaviPresenter : NaviContract.Presenter, BasePresenterKt<NaviContract.View>() {
    override var mModel: NaviContract.Model? = NaviModel()

    override fun requestNaviList() {
        RequestManager.execute(this, mModel?.requestNaviList(),
            object : BaseObserver<List<NaviBean>>() {
                override fun onSuccess(data: List<NaviBean>) {
                    data.run {
                        if (isNullOrEmpty()) {
                            mView?.showEmpty()
                        } else {
                            mView?.onRequestNaviListSuccess(this)
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    mView?.showError()
                }

            })
    }

}
