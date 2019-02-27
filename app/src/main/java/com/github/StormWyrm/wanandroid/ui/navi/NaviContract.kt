package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.base.mvp.ILoadView
import com.github.StormWyrm.wanandroid.base.mvp.IModel
import com.github.StormWyrm.wanandroid.base.mvp.IPresenter
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import io.reactivex.Observable

interface NaviContract {
    interface Model : IModel {
        fun requestNaviList(): Observable<BaseResponse<List<NaviBean>>>
    }

    interface Presenter : IPresenter<View, Model> {
        fun requestNaviList()
    }

    interface View : ILoadView<Presenter> {
        fun onRequestNaviListSuccess(datas: List<NaviBean>)
    }

}
