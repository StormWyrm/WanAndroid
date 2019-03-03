package com.github.StormWyrm.wanandroid.ui.detail.search

import com.github.StormWyrm.wanandroid.base.exception.ResponseException
import com.github.StormWyrm.wanandroid.base.mvp.BasePresenterKt
import com.github.StormWyrm.wanandroid.base.net.RequestManager
import com.github.StormWyrm.wanandroid.base.net.observer.BaseObserver
import com.github.StormWyrm.wanandroid.bean.query.QueryBean

class SearchDetailPresenter : SearchDetailContract.Presenter, BasePresenterKt<SearchDetailContract.View>() {
    override var mModel: SearchDetailContract.Model? = SearchDetailModel()

    override fun requestQueryKey(queryKey: String, pageNum: Int) {
        RequestManager.execute(this, mModel?.requestQueryKey(queryKey, pageNum),
            object : BaseObserver<QueryBean>(false) {
                override fun onSuccess(data: QueryBean) {
                    data.run {
                        if (size == 0) {
                            if (pageNum == 0) {
                                mView?.noData()
                            } else {
                                mView?.noMoreData()
                            }
                        } else {
                            mView?.onQueryKeySuccess(datas)
                        }
                        if (over) {
                            mView?.noMoreData()
                        }
                    }
                }

                override fun onError(e: ResponseException) {
                    if (pageNum == 0) {
                        mView?.loadDataError()
                    } else {
                        mView?.loadMoreError()
                    }
                }

            })
    }


}
