package com.github.StormWyrm.wanandroid.ui.tree.detail

import com.github.StormWyrm.wanandroid.base.mvp.*
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailBean
import com.github.StormWyrm.wanandroid.bean.tree.detail.TreeDetailDataItem
import io.reactivex.Observable

interface TreeDetailContract {
    interface Model : ICollectModel {
        fun requestTreeDetailList(pageNum: Int, cid: Int): Observable<BaseResponse<TreeDetailBean>>
    }

    interface Presenter : ICollectPresenter<View, Model> {
        fun requestTreeDetailList(pageNum: Int, cid: Int)
    }

    interface View : ICollectView<Presenter> {
        fun onRequestTreeDetailListSuccess(datas: List<TreeDetailDataItem>)
    }
}
