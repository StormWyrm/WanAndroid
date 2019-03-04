package com.github.StormWyrm.wanandroid.ui.detail.search

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.query.QueryBean
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import io.reactivex.Observable

interface SearchDetailContract {
    interface View : IListView<Presenter> {
        fun onQueryKeySuccess(data: List<QueryDataItem>)
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestQueryKey(category: Int, queryKey: String, pageNum: Int)
    }

    interface Model : IModel {
        fun requestQueryKey(category: Int, queryKey: String, pageNum: Int): Observable<BaseResponse<QueryBean>>
    }
}
