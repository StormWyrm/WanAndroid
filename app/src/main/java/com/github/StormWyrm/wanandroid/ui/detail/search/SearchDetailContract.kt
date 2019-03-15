package com.github.StormWyrm.wanandroid.ui.detail.search

import com.github.StormWyrm.wanandroid.base.mvp.ICollectModel
import com.github.StormWyrm.wanandroid.base.mvp.ICollectPresenter
import com.github.StormWyrm.wanandroid.base.mvp.ICollectView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.query.QueryBean
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import io.reactivex.Observable

interface SearchDetailContract {
    interface View : ICollectView<Presenter> {
        fun onQueryKeySuccess(data: List<QueryDataItem>)
    }

    interface Presenter : ICollectPresenter<View, Model> {
        fun requestQueryKey(category: Int, queryKey: String, pageNum: Int)
    }

    interface Model : ICollectModel {
        fun requestQueryKey(category: Int, queryKey: String, pageNum: Int): Observable<BaseResponse<QueryBean>>
    }
}
