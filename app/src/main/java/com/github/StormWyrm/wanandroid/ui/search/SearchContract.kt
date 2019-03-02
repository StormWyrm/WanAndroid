package com.github.StormWyrm.wanandroid.ui.search

import com.github.StormWyrm.wanandroid.base.mvp.ILoadView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import com.github.StormWyrm.wanandroid.bean.query.QueryBean
import com.github.StormWyrm.wanandroid.bean.query.QueryDataItem
import io.reactivex.Observable

interface SearchContract {
    interface Model : IModel {
        fun requestHotKey(): Observable<BaseResponse<List<HotKeyBean>>>

        fun requestQueryKey(queryKey: String, pageNum: Int): Observable<BaseResponse<QueryBean>>
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestHotKey()

        fun requestQueryKey(queryKey: String, pageNum: Int)
    }

    interface View : ILoadView<Presenter> {
        fun onQueryHotSuccess(datas: List<HotKeyBean>)

        fun onQueryKeySuccess(data: List<QueryDataItem>)

        fun onQueryKeyEmpty()

        fun onQueryKeyError()

        fun onQueryMoreKeyError()

        fun onQueryMoreKeyEmpty()
    }

}
