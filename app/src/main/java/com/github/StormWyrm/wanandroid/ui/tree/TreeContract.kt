package com.github.StormWyrm.wanandroid.ui.tree

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.TreeBean
import io.reactivex.Observable

interface TreeContract {
    interface Modle : IModel {
        fun requestTreeList(): Observable<BaseResponse<List<TreeBean>>>
    }

    interface Presenter : IPresenter<View, Modle> {
        fun requestTreeList()
    }

    interface View : IListView<Presenter> {
        fun onRequestTreeListSuccess(datas: List<TreeBean>)
    }

}