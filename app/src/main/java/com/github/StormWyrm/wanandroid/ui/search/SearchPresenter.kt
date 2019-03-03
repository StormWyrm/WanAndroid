package com.github.StormWyrm.wanandroid.ui.search

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.HotKeyBean

class SearchPresenter : SearchContract.Presenter, BasePresenterKt<SearchContract.View>() {
    override var mModel: SearchContract.Model? = SearchModel()

    override fun requestHotKey() {
        RequestManager.execute(this, mModel?.requestHotKey(),
            object : BaseObserver<List<HotKeyBean>>() {
                override fun onSuccess(data: List<HotKeyBean>) {
                    mView?.onQueryHotSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.showError()
                }

            })
    }
}
