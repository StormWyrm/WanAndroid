package com.github.StormWyrm.wanandroid.ui.navi

import com.github.StormWyrm.wanandroid.apis.WanAndroidRetrofitHelper
import com.github.StormWyrm.wanandroid.base.mvp.BaseModelKt
import com.github.StormWyrm.wanandroid.bean.BaseResponse
import com.github.StormWyrm.wanandroid.bean.navi.NaviBean
import io.reactivex.Observable

class NaviModel : NaviContract.Model, BaseModelKt() {
    override fun requestNaviList(): Observable<BaseResponse<List<NaviBean>>> {
        return WanAndroidRetrofitHelper.instace.naviList()
    }

}
