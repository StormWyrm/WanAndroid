package com.github.StormWyrm.wanandroid.ui.tree.detail

import com.github.StormWyrm.wanandroid.base.mvp.IListView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.base.mvp.IView
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem
import io.reactivex.Observable

interface TreeDetailContract {
    interface Model : IModel {
        fun requestTreeDetailList(pageNum: Int, cid: Int): Observable<BaseResponse<TreeDetailBean>>
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestTreeDetailList(pageNum: Int, cid: Int)
    }

    interface View : IListView<Presenter> {
        fun onRequestTreeDetailListSuccess(datas: List<TreeDetailDataItem>)
    }
}
