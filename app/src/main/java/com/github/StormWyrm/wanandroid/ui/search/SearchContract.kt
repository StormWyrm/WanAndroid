package com.github.StormWyrm.wanandroid.ui.search

import com.github.StormWyrm.wanandroid.base.mvp.ILoadView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.HotKeyBean
import io.reactivex.Observable

interface SearchContract {
    interface Model : IModel {
        fun requestHotKey(): Observable<BaseResponse<List<HotKeyBean>>>
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestHotKey()
    }

    interface View : ILoadView<Presenter> {
        fun onQueryHotSuccess(datas: List<HotKeyBean>)
    }

}
