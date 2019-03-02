package com.github.StormWyrm.wanandroid.ui.search

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.bean.query.QueryBean

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

    override fun requestQueryKey(queryKey: String, pageNum: Int) {
        RequestManager.execute(this, mModel?.requestQueryKey(queryKey, pageNum),
            object : BaseObserver<QueryBean>(false) {
                override fun onSuccess(data: QueryBean) {
                    data.run {
                        if (size == 0) {
                            if (pageNum == 0) {
                                mView?.onQueryKeyEmpty()
                            } else {
                                mView?.onQueryMoreKeyEmpty()
                            }
                        } else {
                            mView?.onQueryKeySuccess(datas)
                        }
                        if(over){
                            mView?.onQueryMoreKeyEmpty()
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (pageNum == 0) {
                        mView?.onQueryKeyError()
                    } else {
                        mView?.onQueryMoreKeyError()
                    }
                }

            })
    }

}
